const app = new Vue({
  el: "#app",
  data() {

  },
  methods: {
    grantCustomSpace(fileIds, userId, type) {
      // 授权访问
      return axios.get("../paid/grantCustomSpace", {
        params: {
          fileIds: fileIds,
          userId: userId,
          type: type
        }
      });
    },
    grantPreview(fileId, userId, processInstanceId) {
      // 授权预览审批附件
      return axios.get("../paid/grantPreview", {
        params: {
          fileId: fileId,
          userId: userId,
          processInstanceId: processInstanceId
        }
      });
    },
    grantPreview2(fileIdList, userId, processInstanceId) {
      // 授权预览审批附件
      return axios.get("../paid/grantPreview2", {
        params: {
          fileIdList: JSON.stringify(fileIdList),
          userId: userId,
          processInstanceId: processInstanceId
        }
      });
    },
    dentry(fileId, spaceId, userId,) {
      // 授权预览审批附件
      return axios.get("../paid/dentry", {
        params: {
          fileId: fileId,
          spaceId: spaceId,
          userId: userId,
        }
      });
    },
    preview(file) {
      console.log(file);
      if (file.voucherNum == null) {
        this.dentry(file.fileId, file.spaceId, this.user.userId).then(() => {
          dd.biz.cspace.preview({
            corpId: CORP_ID,
            spaceId: file.spaceId,
            fileId: file.fileId,
            fileName: file.fileName,
            fileSize: file.fileSize,
            fileType: file.fileType,
            onSuccess(res) {

            },
            onFail(err) {
              console.log(err);
            }
          });
        });
      } else {
        this.grantPreview(file.fileId, this.user.userId, file.voucherNum).then(() => {
          dd.biz.cspace.preview({
            corpId: CORP_ID,
            spaceId: file.spaceId,
            fileId: file.fileId,
            fileName: file.fileName,
            fileSize: file.fileSize,
            fileType: file.fileType,
            onSuccess(res) {

            },
            onFail(err) {
              console.log(err);
            }
          });
        });
      }


    },
  }
});