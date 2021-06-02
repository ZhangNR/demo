const CORP_ID = "ding803030f55b77d1fd35c2f4657eb6378f";
// const CORP_ID = "ding6ea9b4ac033adbfd35c2f4657eb6378f";

let form = {
  id: '',
  year: '',
  name: '',
  cooperateRatio: '',
  type: '',
  attribute: '',
  deptId: '',
  deptName: '',
  subDeptId: '',
  subDeptName: '',
  customerName: '',
  customerContactName: '',
  customerContactPhone: '',
  basisType: '',
  projectIncome: '',
  creatorId: '',
  creatorName: '',
  createDate: ''
};

let pubForm = {
  id: '',
  projectApprovalId: '',
  num: '',
  name: '',
  pubProjectNum: '',
  pubProjectName: '',
  singleProjectIds: [],
  singleProjects: [],
  sumDesignMoney: '',
  publishMoney: '',
  deviation: 0.0,
  multipleSelection: [],
  multipleSelection2: [],
  fileList: [],
  fileListJSON: ''
};

let params = {
  project: '',
  name: '',
  parentProject: '',
  year: '',
  month: '',
  major: '',
  creatorName: '',
  date: ['', ''],
  note: '',
  currentPage: 1,
  pageSize: 10,
  total: 0,
};

let user = {
  id: '',
  userId: '',
  name: '',
  deptId: '',
  deptName: '',
  subDeptId: '',
  subDeptName: '',
  depts: [],
  subDepts: [],
  departments: [],
  position: ''
};

let vm = new Vue({
  el: "#app",
  data() {
    return {
      loading: true,
      mainLoading: false,
      drawer: false,
      approvalDrawer: false,
      pubDialogVisible: false,
      singleDialogVisible: false,
      singleDrawerVisible: false,
      addButtonController: false,
      pubDialogFormDisabled: false,
      pubExportDialog: false,
      excelDialog: false,
      isLeadControl: {
        operation: false,
      },
      queryType: '0',
      activeName: '0',
      activeIndex: '',
      year: new Date().getFullYear() + '',
      approval: {
        currentPage: 1,
        pageSize: 10,
        total: 0,
      },
      count: 0,
      sum: 0,
      searchValue: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      formLabelWidth: '180px',
      form: form,
      pubForm: pubForm,
      searchForm: params,
      searchParams: params,
      showForm: params,
      exportForm: {
        approval: '1',
        state: '',
      },
      user: user,
      illegalSingleProjectIds: [],
      approvalList: [],
      allApprovalList: [],
      majorList: [],
      pubProjectTable: [],
      readTable: [],
      singleProjectTable: [],
      singleProjectChooseTable: [],
      publishProjectList: [],
      excelData: [{
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-01',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-03',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }],
      extra: {},
      rules: {
        num: [{
          required: true,
          message: '请输入项目编号',
          trigger: 'blur'
        }],
        pubProjectNum: [{
          required: true,
          message: '请输入单册编号',
          trigger: 'blur'
        }],
        pubProjectName: [{
          required: true,
          message: '请输入单册名称',
          trigger: 'blur'
        }],
        publishMoney: [{
          required: true,
          message: '请输入出版勘察设计费（降点后含税）',
          trigger: 'blur'
        }],
        annex: [{
          required: true,
          message: '请上传附件',
          trigger: 'blur'
        }]
      },
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
    }
  },
  methods: {
    inputChange(value) {
      if (isNaN(value)) {
        value = '';
      }
      if (value.indexOf('.') > 0) {
        value = value.slice(0, value.indexOf('.') + 3);
      }
      this.pubForm.publishMoney = value;
    },
    tabClick(name) {
      if (name == 0) {
        this.$message.warning("未找到立项项目时不允许新建单册哦");
        return false;
      }
      this.pageSize = 10;
      this.currentPage = 1;
      this.activeName = name;
      this.mainLoading = true;
      this.approvalList.forEach(item => {
        if (item.id == name) {
          this.form = item;
        }
      });
      this.getPublishProjectList(name);
      this.getCountAndSum(name);
    },
    more() {
      this.drawer = true;
    },
    read2() {
      this.singleDrawerVisible = true;
      if (this.pubForm.singleProjects == null) {
        //获取单项清单
        this.getSingleProjectListByIds(this.pubForm.singleProjectIds, null);
      } else {
        //展示数据
        this.readTable = this.pubForm.singleProjects;
      }
    },
    read(index, row) {
      this.singleDrawerVisible = true;
      if (row.singleProjects == null) {
        //获取单项清单
        this.getSingleProjectListByIds(row.singleProjectIds, index);
      } else {
        //展示数据
        this.readTable = row.singleProjects;
      }
    },
    pubDialogClosed() {
      this.$refs.upload.clearFiles();
    },
    add() {
      this.pubDialogFormDisabled = false;
      this.pubDialogVisible = true;
      if (this.$refs.pubForm) {
        this.$refs.pubForm.resetFields();
      }
      this.pubForm = Object.assign({}, pubForm);
      this.pubForm.singleProjects = [];
      this.pubForm.singleProjectIds = [];
      this.pubForm.fileList = [];
      this.pubForm.projectApprovalId = this.activeName;
    },
    reviseApproval() {
      this.approvalDrawer = true;
      if (this.allApprovalList.length === 0) {
        this.getAllApprovalList();
      }
    },
    switchApproval(approval) {
      this.pubForm.projectApprovalId = approval.id;
      this.approvalDrawer = false;
    },
    handleView(index, row) {
      this.pubDialogFormDisabled = true;
      this.pubForm = Object.assign({}, row);

      if (this.pubForm.fileList == null) {
        // 服务器文件未删除（待解决）
        this.pubForm.fileList = [];
      } else {
        this.pubForm.fileList = JSON.parse(this.pubForm.fileList);
      }
      this.pubDialogVisible = true;
      this.activeIndex = index;
      if (this.$refs.pubForm) {
        this.$refs.pubForm.resetFields();
      }
      this.$nextTick(() => {
        this.$refs.upload.uploadFiles = this.pubForm.fileList;
      });
      this.pubForm.multipleSelection = [];
    },
    handleEdit(index, row) {
      this.pubDialogFormDisabled = false;
      this.pubForm = Object.assign({}, row);

      if (this.pubForm.fileList == null) {
        // 服务器文件未删除（待解决）
        this.pubForm.fileList = [];
      } else {
        this.pubForm.fileList = JSON.parse(this.pubForm.fileList);
      }
      this.pubDialogVisible = true;
      if (this.$refs.pubForm) {
        this.$refs.pubForm.resetFields();
      }

      this.$nextTick(() => {
        this.$refs.upload.uploadFiles = this.pubForm.fileList;
      });

      this.activeIndex = index;
      this.pubForm.multipleSelection = [];
    },
    handleCommit(index, row) {
      this.pubDialogFormDisabled = false;
      this.activeIndex = index;

      // 没保存 点击提交不会走这里，保存的时候已经对上传附件进行了限制，那这里肯定不会为空，这里判断多余；
      // 另外一种，保存时上传附件，再次打开将附件删除，保存提交都会失败，只能关闭取消再次打开文件又显示出来，而实际上文件已经被删除。so. bug is here
      // 解决办法：前端删除文件，显示将文件删掉，并记录删除的文件名，再保存、提交的时候后端去删除文件。
      if (row.fileList == null) {
        this.$message.error("请先上传附件！");
        return false;
      }

      this.$confirm('是否提交?  提交之后进入审批流程', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let data = this.getPubProject(row);
        axios.post('../project/commitProject', data).then(res => {
          if (res.data.code == 0) {
            this.$message.success("提交成功");
            this.pubDialogVisible = false;
            this.mainLoading = true;
            this.getPublishProjectList(this.activeName);
          } else {
            this.$message.error(res.data.message);
          }
        }).catch(error => {
          console.error(error);
        });
      }).catch(() => {
        this.$message.info("已取消提交！");
      });
    },
    handleDelete(index, row) {
      this.pubDialogFormDisabled = false;
      this.activeIndex = index;
      this.$confirm('是否删除，删除后不可恢复？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.get('../project/deleteProject', {
          params: {
            id: row.id
          }
        }).then(res => {
          if (res.data.code == 0) {
            this.$message.success("删除成功！");
            this.mainLoading = true;
            this.getPublishProjectList(this.activeName);
          } else {
            this.$message.error(res.data.message);
          }
        }).catch(error => {
          console.error(error);
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    beforeHandleCommand(index, row, command) {
      return {
        'index': index,
        'row': row,
        'command': command
      }
    },
    handleCommand(command) {
      switch (command.command) {
        case 'view':
          this.handleView(command.index, command.row);
          break;
        case 'edit':
          this.handleEdit(command.index, command.row);
          break;
        case 'commit':
          this.handleCommit(command.index, command.row);
          break;
        case 'del':
          this.handleDelete(command.index, command.row);
          break;
        default:
          console.log("no type");
      }
    },
    approvalCurrentChange() {
      this.getApprovalList();
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.mainLoading = true;
      this.getPublishProjectList(this.activeName);
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.mainLoading = true;
      this.getPublishProjectList(this.activeName);
    },
    handleSizeChange2(val) {
      this.searchParams.pageSize = val;
      this.getSingleProjectList();
    },
    handleCurrentChange2(val) {
      this.searchParams.currentPage = val;
      this.getSingleProjectList();
    },
    handleSizeChange3(val) {
      this.showForm.currentPage = 1;
      this.showForm.pageSize = val;
    },
    handleCurrentChange3(val) {
      this.showForm.currentPage = val;
    },
    beforeUpload(file) {

      return new Promise((resolve, reject) => {
        //判断文件是否符合正则表达式的规则
        if (this.pubForm.pubProjectNum === '') {
          this.$message.error("请先填写单册编号！");
          return reject(false);
        } else {
          this.extra.pubProjectNum = this.pubForm.pubProjectNum;
          this.extra.deptName = this.user.deptName;
          this.extra.subDeptName = this.user.subDeptName;
          return resolve(true);
        }
      });

    },
    uploadSuccess(response, file, fileList) {
      if (response.code === 0) {
        file.url = response.data;
      } else {
        this.$message.error("附件上传失败");
      }
    },
    uploadError(err, file, fileList) {
      this.$message.error("附件上传失败");
    },
    beforeRemove(file, fileList) {
      if (file && file.status === 'success') {
        return this.$confirm(`确定移除 ${file.name} 附件？删除后不可恢复！`);
      }
    },
    fileRemove(file, fileList) {
      //从fileList中移除file
      //根据fileUrl删除服务器的文件
      if (typeof file.url !== 'undefined' && file.url !== '') {
        axios.get('../file/removeFile', {
          params: {
            url: file.url
          }
        }).then(res => {
          if (res.data.code == 0) {
            this.$message.success("附件删除成功！");
          } else {
            this.$message.error("附件删除失败！");
          }
        }).catch(error => {
          console.error(error);
        });
      }

    },
    chooseSingleProject() {
      this.singleDialogVisible = true;
      this.queryType = '0';
      this.typeChange('0');

      if (this.pubForm.id !== '') {
        // 此时已经保存过，那就已经选择过单点了，如果前面没有查询过单点详细信息，这里需要去查询，如果查询过，只需要将结果给已选择table展示，并设置选中
        // 此时查询新的数据，选中，
        //  1 记录被删的id，从ids中删除，从重新生成ids和singleProjects中删除，添加新选中的数据
        //  2 确认选中后将 multipleSelection2中的数据与multipleSelection中合并，重新生成ids和singleProjects；就不用记录哪些单点被删了，还要去记录被删的id，
        console.log(this.pubForm.singleProjects);
        // if (this.pubForm.singleProjects === undefined ||this.pubForm.singleProjects == null || this.pubForm.singleProjects === '') {
        //     if (this.pubForm.singleProjectIds != null && this.pubForm.singleProjectIds.length > 0) {
        //         this.getSingleProjectListByIds(this.pubForm.singleProjectIds, null);
        //     }
        // }

        if (!Array.isArray(this.pubForm.singleProjects)) {
          this.showRead(this.pubForm) && this.getSingleProjectListByIds(this.pubForm.singleProjectIds, null);
        }

      }

      // if (this.pubForm.singleProjectIds != null && this.pubForm.singleProjectIds.length > 0) {
      //     this.getSingleProjectListByIds(this.pubForm.singleProjectIds, null);
      // }
    },
    closeSingleDialog() {
      if (this.$refs.singleTable) {
        this.$refs.singleTable.clearSelection();
        this.pubForm.multipleSelection = [];
      }
    },
    handleSelect(val) {
      // 新添加的单点
      this.pubForm.multipleSelection = [].concat(val);
    },
    handleSelectAll(val) {
      // 新添加的单点
      this.pubForm.multipleSelection = [].concat(val);
    },
    handleSelect2(val) {
      // 已选择的单点集合
      this.pubForm.multipleSelection2 = [].concat(val);
    },
    handleSelectAll2(val) {
      // 已选择的单点集合
      this.pubForm.multipleSelection2 = [].concat(val);
    },
    showRead(row) {
      if (row.singleProjectIds == null) {
        return false;
      }
      return Array.isArray(row.singleProjectIds) ? row.singleProjectIds.length !== 0 : JSON.parse(row.singleProjectIds).length !== 0;

    },
    setDisable(row) {
      return !(row.creatorId === this.user.userId && (row.state === '保存' || row.state === '审核失败'));
    },
    typeChange(val) {
      if (val === '1') {
        console.log("已选择单项");
        this.singleProjectChooseTable = this.pubForm.singleProjects || [];
        this.pubForm.multipleSelection2 = this.pubForm.singleProjects || [];
        this.$nextTick(() => {
          this.singleProjectChooseTable.forEach(item => {
            this.$refs.singleProjectChoose.toggleRowSelection(item, true);
          });
        });

        // 第一次新建时，或者第一次新建未保存再次打开时 pubForm.id 为空，
        /*if (this.pubForm.id == '') {
            if (this.pubForm.multipleSelection.length === 0 && this.pubForm.sumDesignMoney === '') {

            } else {
                // this.pubForm.multipleSelection.forEach(item => this.$refs.singleTable.toggleRowSelection(item, true));
                this.getSingleProjectList();
                this.$nextTick(() => {
                    this.pubForm.multipleSelection2.forEach(item => {
                        this.$refs.singleProjectChoose.toggleRowSelection(item, true);
                    });
                });
            }
        } else {
            // 此时已经保存过，那就已经选择过单点了，如果前面没有查询过单点详细信息，这里需要去查询，如果查询过，只需要将结果给已选择table展示，并设置选中
            // 此时查询新的数据，选中，
            //  1 记录被删的id，从ids中删除，从重新生成ids和singleProjects中删除，添加新选中的数据
            //  2 确认选中后将 multipleSelection2中的数据与multipleSelection中合并，重新生成ids和singleProjects；就不用记录哪些单点被删了，还要去记录被删的id，
            if (this.pubForm.singleProjects == null || this.pubForm.singleProjects == '') {
                if (this.pubForm.singleProjectIds != null && this.pubForm.singleProjectIds.length > 0) {
                    this.getSingleProjectListByIds(this.pubForm.singleProjectIds, null);
                }
            } else {
                this.singleProjectTable = this.pubForm.singleProjects;
                this.pubForm.multipleSelection2 = this.pubForm.singleProjects;
                this.searchForm.total = this.singleProjectTable.length;
                this.$nextTick(() => {
                    this.singleProjectChooseTable.forEach(item => {
                        this.$refs.singleProjectChoose.toggleRowSelection(item, true);
                    });
                });
            }
         }*/

      } else {
        this.singleProjectTable = [];
        this.searchForm.total = 0;
        this.$nextTick(() => {
          this.$refs.singleTable.clearSelection();
        });
      }
    },
    search() {
      //type： 未出版的单项时从数据库查询 type：已选择的单项从singleProjectChooseTable中筛选符合条件的。
      // this.$refs.singleTable.clearSelection();
      this.searchForm.currentPage = 1;
      if (this.queryType === '0') {
        this.searchParams = Object.assign({}, this.searchForm);
        this.getSingleProjectList();
      } else {
        if (this.pubForm.singleProjects === undefined) {
          return false;
        } else if (Array.isArray(this.pubForm.singleProjects)) {
          if (this.pubForm.singleProjects.length === 0) {
            return false;
          }
        } else {
          this.pubForm.singleProjects = JSON.parse(this.pubForm.singleProjects);
          if (this.pubForm.singleProjects.length === 0) {
            return false;
          }
        }

        this.showForm = Object.assign({}, this.searchForm);
        if (this.showForm.date == null) {
          this.showForm.date = ['', ''];
        }
        this.singleProjectChooseTable = [].concat(this.pubForm.singleProjects);

        if (this.showForm.project.trim() !== '') {
          this.singleProjectChooseTable = this.singleProjectChooseTable.filter(item => {
            if (item.parentProject != null) {
              return item.parentProject.includes(this.showForm.project.trim())
            }
          });
        }
        if (this.showForm.name.trim() !== '') {
          this.singleProjectChooseTable = this.singleProjectChooseTable.filter(item => item.name.includes(this.showForm.name.trim()));
        }
        if (this.showForm.year.trim() !== '') {
          this.singleProjectChooseTable = this.singleProjectChooseTable.filter(item => item.year == this.showForm.year.trim());
        }
        if (this.showForm.month.trim() !== '') {
          this.singleProjectChooseTable = this.singleProjectChooseTable.filter(item => item.month == this.showForm.month.trim());
        }
        if (this.showForm.major.trim() !== '') {
          this.singleProjectChooseTable = this.singleProjectChooseTable.filter(item => {
            if (item.major != null) {
              return item.major == this.showForm.major;
            }
          });
        }
        if (this.showForm.creatorName.trim() !== '') {
          this.singleProjectChooseTable = this.singleProjectChooseTable.filter(item => {
            if (item.creatorName != null) {
              return item.creatorName == this.showForm.creatorName.trim();
            }
          });
        }
        if (this.showForm.note.trim() !== '') {
          this.singleProjectChooseTable = this.singleProjectChooseTable.filter(item => {
            if (item.note != null) {
              return item.note.includes(this.showForm.note.trim());
            }
          });
        }
        if (this.showForm.date[0] !== '' && this.showForm.date[1] !== '') {
          this.singleProjectChooseTable = this.singleProjectChooseTable.filter(item => {
            if (item.surveyDate != null) {
              return isDateBetween(item.surveyDate, this.showForm.date[0], this.showForm.date[1])
            }
          });
        }
      }


      // if (this.pubForm.id == '') {
      //     this.$refs.singleTable.clearSelection();
      // }
    },
    confirm() {

      if (this.queryType === '0') {

        if (this.pubForm.multipleSelection.length === 0) {
          this.$message.error("请选择单项后确定！");
          return false;
        }

        this.$confirm('确认是否添加? 确定后跳转 [已选择单项] 确认', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {

          if (this.pubForm.singleProjectIds === undefined || this.pubForm.singleProjectIds == null) {
            this.pubForm.singleProjectIds = [];
          } else if (!Array.isArray(this.pubForm.singleProjectIds)) {
            this.pubForm.singleProjectIds = JSON.parse(this.pubForm.singleProjectIds);
          }

          if (this.pubForm.singleProjects === undefined) {
            this.pubForm.singleProjects = [];
          } else if (!Array.isArray(this.pubForm.singleProjects)) {
            this.pubForm.singleProjects = JSON.parse(this.pubForm.singleProjects)
          }


          this.pubForm.multipleSelection.forEach(item => {
            this.pubForm.singleProjectIds.push(item.id);
            this.pubForm.singleProjects.push(item);
          });

          this.$refs.singleTable.clearSelection();
          this.pubForm.multipleSelection = [];

          this.queryType = '1';
          this.typeChange('1');

        }).catch(e => {
          console.log(e);
          this.$message.info('已取消添加!');
        });

      } else {

        if (this.pubForm.multipleSelection2.length === 0) {
          this.$message.error("请选择单项后确定！");
          return false;
        }
        let sumDesignMoney = 0;
        this.pubForm.singleProjectIds = [];
        this.pubForm.singleProjects = [];
        sumDesignMoney = this.pubForm.multipleSelection2.reduce((sumDesignMoney, item) => getSum(sumDesignMoney, item.designMoney), 0);
        this.pubForm.sumDesignMoney = sumDesignMoney;
        this.pubForm.multipleSelection2.forEach(item => {
          this.pubForm.singleProjectIds.push(item.id);
          this.pubForm.singleProjects.push(item);
        });

        this.$refs.singleProjectChoose.clearSelection();
        this.singleDialogVisible = false;

        /*this.$confirm('确认是否添加?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            let sumDesignMoney = 0;
            this.pubForm.singleProjectIds = [];
            this.pubForm.singleProjects = [];
            let allSingleProjects = this.pubForm.multipleSelection.concat(this.pubForm.multipleSelection2);
            sumDesignMoney = allSingleProjects.reduce((sumDesignMoney, item) => getSum(sumDesignMoney, item.designMoney), 0);
            this.pubForm.sumDesignMoney = sumDesignMoney;
            allSingleProjects.forEach(item => {
                this.pubForm.singleProjectIds.push(item.id);
                this.pubForm.singleProjects.push(item);
            });
            this.singleDialogVisible = false;
        }).catch(() => {
            this.$message.info('已取消添加!');
        });*/

      }


    },
    cancel() {
      this.singleDialogVisible = false;

      if (this.queryType === '0') {

      } else {
        if (this.pubForm.id === '') {
          this.pubForm.singleProjects = [];
          this.pubForm.singleProjectIds = [];
        }

      }
    },
    save(formName) {
      this.$refs[formName].validate(valid => {
        if (!valid) {
          return false;
        }

        if (!(this.pubForm.singleProjectIds != null && this.pubForm.singleProjectIds.length !== 0)) {
          this.$message.error("单项数量为空！请选择单项");
          return false;
        }

        if (this.$refs.upload.uploadFiles.filter(file => file.status === 'success').length === 0) {
          this.$message.error("请上传附件");
          return false;
        }

        this.pubForm.state = "保存";
        this.$confirm('是否保存?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let data = Object.assign({}, this.pubForm);
          if (Array.isArray(this.pubForm.singleProjectIds)) {
            data.singleProjectIds = JSON.stringify(this.pubForm.singleProjectIds);
          }
          let fileListData = [];
          this.$refs.upload.uploadFiles.forEach(item => {
            if (item.status === 'success') {
              fileListData.push({
                name: item.name,
                url: item.url
              });
            }
          });

          data.fileList = JSON.stringify(fileListData);
          data.creatorId = this.user.userId;
          data.creatorName = this.user.name;

          axios.post('../project/saveProject', data).then(res => {
            if (res.data.code == 0) {
              this.illegalSingleProjectIds = [];
              this.$message.success("保存成功");
              this.pubDialogVisible = false;
              this.mainLoading = true;
              this.getPublishProjectList(this.activeName);
            } else {
              this.illegalSingleProjectIds = res.data.data;
              this.$message.error(res.data.message);
            }
          }).catch(error => {
            console.error(error);
          });
        }).catch(() => {
          this.$message.info("已取消保存");
        });
      });
    },
    commit(formName) {
      this.$refs[formName].validate((valid) => {
        if (!valid) {
          return false;
        }

        if (!(this.pubForm.singleProjectIds != null && this.pubForm.singleProjectIds.length !== 0)) {
          this.$message.error("单项数量为空！请选择单项");
          return false;
        }

        if (this.$refs.upload.uploadFiles.filter(file => file.status === 'success').length === 0) {
          this.$message.error("请上传附件");
          return false;
        }

        this.$confirm('是否提交?  提交之后进入审批流程', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let data = this.getPubProject(this.pubForm);
          axios.post('../project/commitProject', data).then(res => {
            if (res.data.code == 0) {
              this.illegalSingleProjectIds = [];
              this.$message.success("提交成功！");
              this.pubDialogVisible = false;
              this.mainLoading = true;
              this.getPublishProjectList(this.activeName);
            } else {
              this.illegalSingleProjectIds = [];
              this.$message.error(res.data.message);
            }
          }).catch(error => {
            console.error(error);
          });
        }).catch(() => {
          this.$message.info("已取消提交");
        });
      });
    },
    deletePub(formName) {
      if (this.pubForm.id == '') {
        this.$message({
          type: 'warning',
          message: "项目未保存，无法删除"
        });
        return;
      }

      this.$confirm('是否删除，删除后不可恢复？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.get('../project/deleteProject', {
          params: {
            id: this.pubForm.id
          }
        }).then(res => {
          if (res.data.code == 0) {
            this.$message.success("删除成功！");
            this.pubDialogVisible = false;
            this.getPublishProjectList(this.activeName);
          } else {
            this.$message.error(res.data.message);
          }
        }).catch(error => {
          console.error(error);
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    getRowKeys(row) {
      return row.id;
    },
    tableRowClassName({row, rowIndex}) {
      if (row.pubState == '未出版') {
        return ''
      } else {
        if (row.pubState == '已锁定' && this.illegalSingleProjectIds.indexOf(row.id) == -1) {
          return '';
        } else {
          return 'error-row'
        }
      }
    },
    deptInit() {
      let depts = [];
      let subDepts = [];
      if (this.user.departments.length > 1) {
        this.user.departments.forEach(item => {
          if (depts.indexOf(item.deptName) == -1) {
            depts.push(item.deptName);
          }
        });
        this.user.departments.forEach(item => {
          if (item.deptName == this.user.deptName) {
            if (item.subDeptId == 0) {
              subDepts.push({
                subDeptId: 0,
                subDeptName: "",
              });
            } else {
              subDepts.push({
                subDeptId: item.subDeptId,
                subDeptName: item.subDeptName,
              });
            }
          }
        });
        this.user.depts = depts;
        this.user.subDepts = subDepts;
      } else {
        this.user.depts = depts;
        this.user.subDepts = subDepts;
      }

    },
    deptChange() {
      this.user.subDepts = [];
      this.allApprovalList = [];
      this.user.departments.forEach(item => {
        if (item.deptName == this.user.deptName) {
          this.user.deptId = item.deptId;
          if (item.subDeptId == 0) {
            this.user.subDepts.push({
              subDeptId: item.subDeptId,
              subDeptName: "",
            });
          } else {
            this.user.subDepts.push({
              subDeptId: item.subDeptId,
              subDeptName: item.subDeptName,
            });
          }
        }
      });
      if (this.user.subDepts.length > 0) {
        this.user.subDeptId = this.user.subDepts[0].subDeptId;
        this.user.subDeptName = this.user.subDepts[0].subDeptName;
      } else {
        this.user.subDeptId = 0;
        this.user.subDeptName = "";
      }
      this.isLeader(this.user.deptId, this.user.subDeptId);
      this.mainLoading = true;
      this.getApprovalList();
    },
    subDeptChange() {
      this.user.subDepts.forEach(item => {
        if (item.subDeptName == this.user.subDeptName) {
          this.user.subDeptId = item.subDeptId;
        }
      });
      this.isLeader(this.user.deptId, this.user.subDeptId);
      this.mainLoading = true;
      this.getApprovalList();
    },
    isLeader(deptId, subDeptId) {
      this.user.isLeader = this.user.departments.find(department => department.deptId == deptId && department.subDeptId == subDeptId).isLeader || false;
    },
    yearChange() {
      this.mainLoading = true;
      this.getApprovalList();
    },
    getPubProject(data) {
      if (Array.isArray(data.fileList)) {
        let fileListData = [];
        this.$refs.upload.uploadFiles.forEach(item => {
          if (item.status === 'success') {
            fileListData.push({
              name: item.name,
              url: item.url
            });
          }
        });

        data.fileListJSON = JSON.stringify(fileListData);
      } else {
        data.fileListJSON = data.fileList;
      }

      return {
        id: data.projectApprovalId,
        pubId: data.id,
        deptId: this.user.deptId,
        deptName: this.user.deptName,
        subDeptId: this.user.subDeptId,
        subDeptName: this.user.subDeptName,
        num: data.num,
        name: this.form.name,
        pubProjectNum: data.pubProjectNum,
        pubProjectName: data.pubProjectName,
        state: '提交',
        singleProjectIds: Array.isArray(data.singleProjectIds) ? JSON.stringify(data.singleProjectIds) : data.singleProjectIds,
        sumDesignMoney: data.sumDesignMoney,
        publishMoney: data.publishMoney,
        deviation: data.deviation,
        fileList: data.fileListJSON,
        creatorId: this.user.userId,
        creatorName: this.user.name
      }
    },
    getMajorList() {
      axios.get('../project/getMajorList').then(res => {
        this.majorList = res.data;
      }).catch(error => {
        console.error(error);
      });
    },
    getApprovalList() {
      axios.post('../approval/getApprovalList', {
        year: this.year,
        user: this.user,
        pageSize: this.approval.pageSize,
        pageNumber: this.approval.currentPage
      }).then(res => {
        this.approvalList = res.data.records;
        this.approval.total = res.data.total;
        this.mainLoading = false;
        if (this.approvalList.length > 0) {
          this.addButtonController = false;
          this.form = Object.assign({}, this.approvalList[0]);
          this.activeName = this.form.id;
          this.getPublishProjectList(this.form.id);
          this.getCountAndSum(this.form.id);
        } else {
          this.form = Object.assign({}, form);
          this.activeName = 0;
          this.currentPage = 1;
          this.total = 0;
          this.count = 0;
          this.sum = 0;
          this.publishProjectList = [];
          this.addButtonController = true;
          this.$message.warning("未找到立项项目时不允许新建单册哦");
        }
      }).catch(error => {
        console.error(error)
      });
    },
    getAllApprovalList() {
      axios.get('../approval/getAllApprovalList', {
        params: {
          deptId: this.user.deptId
        }
      }).then(res => {
        this.allApprovalList = res.data;
      }).catch(error => {
        console.error(error)
      });
    },
    searchPublishProjects() {
      this.currentPage = 1;
      this.getPublishProjectList(this.activeName);
    },
    getPublishProjectList(id) {
      axios.post('../project/getPublishProjectList', {
        projectApprovalId: id,
        user: this.user,
        name: this.searchValue,
        pageNumber: this.currentPage,
        pageSize: this.pageSize,
      }).then(res => {
        this.mainLoading = false;
        this.publishProjectList = res.data.records;
        this.total = res.data.total;
      }).catch(error => {
        console.error(error);
      });
    },
    getSingleProjectListByIds(ids, index) {
      axios.post('../single/getSingleProjectListByIds', {
        singleProjectIds: Array.isArray(ids) ? JSON.stringify(ids) : ids
      }).then(res => {
        this.readTable = res.data.data;
        if (index == null) {
          this.publishProjectList[this.activeIndex].singleProjects = res.data.data;
          this.pubForm.singleProjects = res.data.data;
        } else {
          // this.$set(this.pubProjectTable, index, newValue);
          this.publishProjectList[index].singleProjects = res.data.data;
        }
      }).catch(error => {
        console.error(error);
      });
    },
    getCountAndSum(index) {
      if (index == '0') {
        this.count = 0;
        this.sum = 0;
      } else {
        axios.get('../project/getCountAndSum', {
          params: {
            id: index
          }
        }).then(res => {
          this.count = res.data.count;
          this.sum = res.data.sum;

        }).catch(error => {
          console.error(error);
        });
      }

    },
    getSingleProjectList() {
      if (this.searchParams.date == null) {
        this.searchParams.date = ['', ''];
      }
      axios.post('../single/getSingleProjectList', {
        "project": this.searchParams.project.trim(),
        "name": this.searchParams.name.trim(),
        "year": this.searchParams.year.trim(),
        "month": this.searchParams.month.trim(),
        "major": this.searchParams.major,
        "creatorName": this.searchParams.creatorName.trim(),
        "startDate": this.searchParams.date[0],
        "endDate": this.searchParams.date[1],
        "note": this.searchParams.note.trim(),
        "pageSize": this.searchParams.pageSize,
        "pageNumber": this.searchParams.currentPage,
        "ids": Array.isArray(this.pubForm.singleProjectIds) ? this.pubForm.singleProjectIds : JSON.parse(this.pubForm.singleProjectIds),
        "user": {
          "userId": this.user.userId,
          "name": this.user.name,
          "deptId": this.user.deptId,
          "deptName": this.user.deptName,
          "subDeptId": this.user.subDeptId,
          "position": this.user.position
        }
      }).then(res => {
        this.singleProjectTable = [];
        this.singleProjectTable = res.data.records;
        this.searchForm.total = res.data.total;
      }).catch(error => {
        console.error(error)
      });
    },
    exportPub() {
      this.pubExportDialog = true;
    },
    exportPubExcel() {
      axios.post('../project/exportPub', {
        approval: this.exportForm.approval === '1' ? '0' : this.activeName,
        state: this.exportForm.state,
        user: this.user
      }, {
        responseType: 'blob',
        responseEncoding: 'utf8',
      }).then(response => {
        const blob = new Blob([response.data], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8'});
        const aEle = document.createElement('a');
        const href = window.URL.createObjectURL(blob);       // 创建下载的链接
        aEle.href = href;
        aEle.download = this.user.deptName + "出版";  // 下载后文件名
        document.body.appendChild(aEle);
        aEle.click();     // 点击下载
        document.body.removeChild(aEle); // 下载完成移除元素
        window.URL.revokeObjectURL(href); // 释放掉blob对象
        this.pubExportDialog = false;
      }).catch(error => {
        console.error(error);
      });
    },
    importModule() {
      /*let wb = XLSX.utils.book_new();
      let ws = XLSX.utils.json_to_sheet([], {
          header: ['立项编号', '单册编号', '出版册名', '总设计费', '出版费']
      });
      XLSX.utils.book_append_sheet(wb, ws, "新增单册");
      let ws2 = XLSX.utils.json_to_sheet([], {
          header: ['系统编号', '部门编号', '部门', '项目组编号', '项目组', '大项', '名称', '年份', '月份', '勘察日期', '专业', '设计费', '设计人员', '附件', '备注', '状态', '出版状态', '创建人', '创建时间', '更新时间', '单册编号']

      });
      XLSX.utils.book_append_sheet(wb, ws2, "单点数据");
      XLSX.writeFile(wb, "出版便捷录入模板.xlsx");*/
      const loadingExcelData = this.$loading({
        lock: true,
        text: '获取数据中...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      axios.post('../project/exportModule', {
        user: this.user
      }, {
        responseType: 'blob',
        responseEncoding: 'utf8',
      }).then(response => {
        loadingExcelData.close();
        const blob = new Blob([response.data], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8'});
        const aEle = document.createElement('a');
        const href = window.URL.createObjectURL(blob);       // 创建下载的链接
        aEle.href = href;
        aEle.download = "出版便捷录入模板";  // 下载后文件名
        document.body.appendChild(aEle);
        aEle.click();     // 点击下载
        document.body.removeChild(aEle); // 下载完成移除元素
        window.URL.revokeObjectURL(href); // 释放掉blob对象
        this.pubExportDialog = false;
      }).catch(error => {
        console.error(error);
      });
    },
    importExcel(response, file, fileList) {
      console.log(response);
      if (response.code !== 0) {
        this.$message.error(response.message);
      } else {
        this.excelDialog = true;
        this.excelData = response.data;
      }
    },
    readExcel(file) {
      const excelLoading = this.$loading({
        lock: true,
        text: 'Excel数据加载中...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      if (!file) {
        return false;
      } else if (!/\.(xls|xlsx|csv)$/.test(file.name.toLowerCase())) {
        this.$message.error('文件格式不正确!');
        return false;
      }
      // this.loading = true;

      const fileReader = new FileReader();
      fileReader.onload = ev => {
        try {
          const data = ev.target.result;
          const workbook = XLSX.read(data, {
            type: 'binary',
            cellText: false, cellDates: true
          });
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets['新增单册']);
          const ws2 = XLSX.utils.sheet_to_json(workbook.Sheets['单点数据'], {raw: false, dateNF: 'yyyy-mm-dd'});

          console.log(ws);
          if (ws) {
            let ps = [];
            ws.forEach(e => {
              let p = {};
              p.projectApprovalId = e['立项编号'];
              p.pubProjectNum = e['单册编号'];
              p.pubProjectName = e['单册名称'];
              p.publishMoney = e['出版费'].toFixed(2);

              let singleProjects = ws2.filter(w => w['单册编号'] === e['单册编号']).map(s => {
                return {
                  id: s['系统编号'],
                  parentProject: s['大项'],
                  name: s['名称'],
                  year: s['年份'],
                  month: s['月份'],
                  surveyDate: s['勘察日期'],
                  major: s['专业'],
                  designMoney: s['设计费'],
                  note: s['备注']
                }
              });
              p.singleProjectIds = singleProjects.map(s => s.id);
              p.singleProjects = singleProjects;
              p.sumDesignMoney = singleProjects.reduce((pre, n) => new Big(pre).plus(n.designMoney).toNumber(), 0);
              p.deviation = new Big(p.sumDesignMoney).minus(p.publishMoney).abs().div(p.publishMoney).round(2).toNumber();
              ps.push(p);
            });
            // this.loading = false;
            this.excelData = ps;
            this.excelDialog = true;
          }

        } catch (e) {
          this.$message.error("Excel数据格式有误！");
          console.log(e);
          return false;
        }
      };
      excelLoading.close();
      fileReader.readAsBinaryString(file);
      return false;
    },
    excelOk() {
      let pubProjectList = this.excelData.map(pub => {
        return {
          state: '保存',
          projectApprovalId: pub.projectApprovalId,
          pubProjectNum: pub.pubProjectNum,
          pubProjectName: pub.pubProjectName,
          singleProjectIds: JSON.stringify(pub.singleProjectIds.map(id => Number(id))),
          sumDesignMoney: pub.sumDesignMoney,
          publishMoney: pub.publishMoney,
          deviation: pub.deviation,
          creatorId: this.user.userId,
          creatorName: this.user.name,
        }
      });
      axios.post('../project/batchSaveProject', JSON.stringify(pubProjectList), {
        headers: {'Content-Type': 'application/json'},
      }).then(res => {
        this.excelDialog = false;
        this.mainLoading = true;
        this.getApprovalList();
      }).catch(error => {
        console.error(error)
      });

    },

  },
  computed: {
    deviation() {
      let calcDeviation = 1;
      if (this.pubForm.sumDesignMoney != null && this.pubForm.sumDesignMoney != 0) {
        // calcDeviation = (Math.abs((this.pubForm.sumDesignMoney * 100 - this.pubForm.publishMoney * 100)) / (this.pubForm.sumDesignMoney * 100)).toFixed(2);
        calcDeviation = jsdivision(Math.abs(jssub(this.pubForm.sumDesignMoney, this.pubForm.publishMoney)), this.pubForm.sumDesignMoney).toFixed(2);
      }
      this.pubForm.deviation = calcDeviation;
      return (calcDeviation * 100).toFixed(2) + '%';
    },
    deviationClass() {
      return this.pubForm.deviation > 0.1 ? 'danger' : 'success';
    },
    totalDesignMoney() {
      let totalDesignMoney = this.readTable.reduce((pre, single) => pre + single.designMoney, 0);
      return Number.parseInt(totalDesignMoney * 100) / 100
    },
    difference() {
      return Math.round((this.pubForm.sumDesignMoney - this.pubForm.publishMoney) * 100) / 100
    }
  },
  watch: {
    'user.isLeader': function (val) {
      this.pubDialogFormDisabled = val;
    }
  },
  mounted() {
    this.year = "2020";

    this.user = {
      "userId": "060050056624072956",
      "name": "张具平",
      "deptId": 84581057,
      "deptName": "设计三所",
      "subDeptId": 0,
      "subDeptName": "",
      "departments": [{
        "deptId": 112252654,
        "deptName": "安徽项目部",
        "subDeptId": 153422358,
        "subDeptName": "合肥移动项目组",
        "isLeader": false
      }, {"deptId": 84581057, "deptName": "设计三所", "subDeptId": 0, "subDeptName": "", "isLeader": true}],
      "position": "研发人员",
      "isLeader": true,
      "depts": ["安徽项目部", "设计三所"],
      "subDepts": [{"subDeptId": 0, "subDeptName": ""}]
    };

    this.loading = false;
    this.getApprovalList();
    // _this.searchForm.total = _this.singleProjectTable.length;
    this.getMajorList();


    /*let _this = this;
    dd.ready(function () {
        dd.runtime.permission.requestAuthCode({
            corpId: CORP_ID,
            onSuccess: function (result) {
                axios.get("../user/login?code=" + result.code)
                    .then(function (response) {
                        if (response.data.code === 0) {
                            _this.user = Object.assign({}, response.data.data);
                            _this.deptInit();

                            _this.loading = false;
                            _this.getApprovalList();
                            // _this.searchForm.total = _this.singleProjectTable.length;
                            _this.getMajorList();

                        } else {
                            _this.$alert(response.data.data, '提示', {
                                confirmButtonText: '确定',
                            });
                        }

                    }).catch(function (error) {
                    console.error(error);
                })
            },
            onFail: function (error) {
                console.error(error);
            }

        });
    });*/
  }
});

function getSum(total, num) {
  return parseInt(total * 1000 + num * 1000) / 1000;
}

/**
 * 加法
 * @param { number } arg1
 * @param { number } arg2
 */
function jsadd(arg1, arg2) {
  let r1, r2, m;
  try {
    r1 = arg1.toString().split(".")[1].length
  } catch (e) {
    r1 = 0
  }
  try {
    r2 = arg2.toString().split(".")[1].length
  } catch (e) {
    r2 = 0
  }
  m = Math.pow(10, Math.max(r1, r2));
  return (arg1 * m + arg2 * m) / m
}

/**
 * 减法
 * @param { number } arg1
 * @param { number } arg2
 */
function jssub(arg1, arg2) {
  let r1, r2, m, n;
  try {
    r1 = arg1.toString().split(".")[1].length
  } catch (e) {
    r1 = 0
  }
  try {
    r2 = arg2.toString().split(".")[1].length
  } catch (e) {
    r2 = 0
  }
  m = Math.pow(10, Math.max(r1, r2));
  n = (r1 >= r2) ? r1 : r2;
  return Number(((arg1 * m - arg2 * m) / m).toFixed(n));
}

/**
 * 除法
 * @param { number } num1
 * @param { number } num2
 */
function jsdivision(num1, num2) {
  let t1, t2, r1, r2;
  try {
    t1 = num1.toString().split('.')[1].length;
  } catch (e) {
    t1 = 0;
  }
  try {
    t2 = num2.toString().split(".")[1].length;
  } catch (e) {
    t2 = 0;
  }
  r1 = Number(num1.toString().replace(".", ""));
  r2 = Number(num2.toString().replace(".", ""));
  return (r1 / r2) * Math.pow(10, t2 - t1);
}

function isDateBetween(dateString, startDateString, endDateString) {
  return (new Date(dateString) > new Date(startDateString)) && (new Date(dateString) < new Date(endDateString))

}

function strip(num, precision) {
  if (precision === void 0) {
    precision = 15;
  }
  return +parseFloat(Number(num).toPrecision(precision));
}