<template>
  <div>
    <van-uploader
      v-model="uploader"
      multiple
      v-bind="$attrs"
      :before-read="beforeRead"
      :after-read="afterRead"
      class="van-cell-uploader"
      upload-icon="plus"
      :accept="accept"
      @delete="handlerDelete"
    >
      <template #preview-cover="{ file }">
        <div
          class="preview-cover"
          v-if="file.id"
          @click="onUploadViewFile(file)"
        >
          点击下载
        </div>
      </template>
    </van-uploader>
  </div>
</template>
<script>
import { Toast } from "vant";
import BaseUI from "@/views/components/baseUI";
import { validatenull } from "@/utils/validate";

import { fileUploadById, listSysFileAll } from "@/api/sys/sysFile";

export default {
  extends: BaseUI,
  name: "vanUploadFile",
  model: {
    prop: "value",
    event: "onVanFileUpload",
  },
  data() {
    return {
      uploader: this.value,
      uploads: [],
      deletes: [],
      filesNum: [],
    };
  },

  props: {
    objectId: {
      type: String | Number,
    },
    fileSizes: {
      type: String | Number,
    },
    fileFormats: {
      type: Array,
    },
    // file组件可以自定义更多文件类型
    accept: {
      type: String,
      default() {
        return ".doc,.docx,.vue";
      },
    },
  },
  methods: {
    // 上传前预处理，与PC端保持一致
    beforeRead(fileMain) {
      let bol = !Array.isArray(fileMain);
      let sufAry = bol ? [fileMain] : fileMain;
      for (let i = 0; i < sufAry.length; i++) {
        const file = sufAry[i];
        const suffix = "." + file.name.split(".")[1];
        //判断是否符合文件大小的限制需求
        let isLt2M = true;
        if (this.fileSizes) {
          isLt2M = file.size / 1024 / 1024 < this.fileSizes;
        }
        //判断是否符合文件类型需求
        let isLtType = false;
        if (this.fileFormats) {
          isLtType = this.fileFormats.indexOf(suffix) >= 0;
        } else {
          this.fileFormats = this.accept.split(",");
          isLtType = this.fileFormats.indexOf(suffix) >= 0;
        }
        if (!isLtType) {
          Toast(`上传文件格式必须为${this.fileFormats}`);
          return false;
        }
        if (!isLt2M) {
          Toast(`上传文件大小不能超过${this.fileSizes}M!`);
          return false;
        }
        return true;
      }
    },
    // 上传后处理，多文件上传后，回调会有数组的情况  只上传非回显的文件
    afterRead(fileMain) {
      let bol = !Array.isArray(fileMain);
      let sufAry = bol ? [fileMain] : fileMain;
      sufAry.forEach((file) => {
        this.uploads.push(file);
      });

      this.$emit("onVanFileUpload", {
        uploads: this.uploads,
        deletes: this.deletes,
      });
    },
    // 删除只能一个一个删，不存在回调是数组的情况
    handlerDelete(file, detail) {
      // 组件内删除：存在id后台已有文件，无id则是还未上传的文件
      if (file.id) {
        this.deletes.push(file.id);
      } else {
        this.uploads.splice(detail.index - this.filesNum, 1);
      }
      this.$emit("onVanFileUpload", {
        uploads: this.uploads,
        deletes: this.deletes,
      });
    },
    onUploadViewFile(file) {
      // 下载
      fileUploadById(file.id)
        .then((responseData) => {
          const content = responseData;
          this.download(content, file.name);
        })
        .catch((error) => {
          this.outputError(error);
        });
    },
    //下载方法
    download(content, fileName) {
      const blob = new Blob([content]);
      const url = window.URL.createObjectURL(blob);
      let dom = document.createElement("a");
      dom.style.display = "none";
      dom.href = url;
      dom.setAttribute("download", fileName);
      document.body.appendChild(dom);
      dom.click();
    },
    // 查询系统文件
    getSysFileAll() {
      this.setLoad();
      let file_search = {
        params: [
          { columnName: "object_id", queryType: "=", value: this.objectId },
        ],
      };
      listSysFileAll(file_search)
        .then((responseData) => {
          if (responseData.code == 100) {
            let allFile = responseData.data;
            let viewAry = [];
            // 组装回显，Vant认识的数据结构，此处只做预览
            allFile.forEach((item) => {
              viewAry.push({
                content: `data:application/octet-stream;base64,${item.fileByte}`,
                file: {
                  name: item.name,
                  id: item.id,
                },
                ...item,
              });
            });
            this.uploader = viewAry;
            this.filesNum = this.uploader.length;
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad();
        })
        .catch((error) => {
          this.outputError(error);
        });
    },
    // 获取文件列表
    getFileList() {
      return this.uploader;
    },
  },
  mounted() {
    this.uploader = [];
    this.uploads = [];
    this.deletes = [];
    if (!validatenull(this.objectId)) {
      this.getSysFileAll();
    }
  },
};
</script>
<style lang="scss">
.preview-cover {
  position: absolute;
  width: 100%;
  text-align: center;
  bottom: 0;
  background-color: #68686861;
}
</style>