let tableColumn = [{
  prop: 'name',
  label: '姓名',
  width: '80',
  align: 'center',
  sortable: false,
  show: true
}, {
  prop: 'positionLevel',
  label: '岗位',
  width: '80',
  align: 'center',
  sortable: false,
  show: false
}, {
  prop: 'deptName',
  label: '设计所',
  width: '120',
  align: 'center',
  sortable: false,
  show: true
}, {
  prop: 'teamName',
  label: '驻点',
  width: '150',
  align: 'center',
  sortable: false,
  show: true
}, {
  prop: 'contribution',
  label: '贡献产值',
  width: '150',
  align: 'right',
  sortable: false,
  show: true
}, {
  prop: 'settlement',
  label: '结算值',
  width: '150',
  align: 'right',
  sortable: false,
  show: true
}, {
  prop: 'workingDay',
  label: '工作日',
  width: '70',
  align: 'center',
  sortable: false,
  show: true
}, {
  prop: 'laborCost',
  label: '人力成本',
  width: '100',
  align: 'right',
  sortable: false,
  show: true
}, {
  prop: 'expensePerson',
  label: '个人费用',
  width: '100',
  align: 'right',
  sortable: false,
  show: true
}, {
  prop: 'expensePublic',
  label: '驻点公共分摊',
  width: '120',
  align: 'right',
  sortable: false,
  show: false
}, {
  prop: 'expensePublicRent',
  label: '驻点房租分摊',
  width: '120',
  align: 'right',
  sortable: false,
  show: false
}, {
  prop: 'expensePublicReplace',
  label: '驻点代付分摊',
  width: '120',
  align: 'right',
  sortable: false,
  show: false
}, {
  prop: 'expensePublicInside',
  label: '所内费用分摊',
  width: '120',
  align: 'right',
  sortable: false,
  show: false
}, {
  prop: 'cost',
  label: '成本合计',
  width: '150',
  align: 'right',
  sortable: false,
  show: true
}, {
  prop: 'contributionProductivity',
  label: '贡献产值成本比',
  width: '150',
  align: 'center',
  sortable: true,
  show: true
}, {
  prop: 'settlementRatio',
  label: '公司结算值成本比',
  width: '160',
  align: 'center',
  sortable: true,
  show: true
}, {
  prop: 'benefit',
  label: '效益',
  width: '120',
  align: 'center',
  sortable: true,
  show: true
}];

let vm = new Vue({
  el: '#app',
  data() {
    return {
      value: '',
      loading: true,
      drawer: false,
      tableHeight: window.innerHeight - 260 + 'px',
      tableHeight2: window.innerHeight - 314 + 'px',
      tableColumn: tableColumn,
      tableColumn2: tableColumn.filter(col => col.show),
      form: {
        deptId: 0,
        teamId: '',
        year: new Date().getFullYear() + ''
      },
      activeName: '1',
      activeNameMain: 'all',
      tableData: [],
      depts: [],
      deptList: [],
      teamList: [],
      monthlyData: [],
      yearData: [],
    }
  },
  methods: {
    tabClick() {
      this.getSomeCost();
    },
    handleClick() {
      this.activeNameMain === 'all' ? this.getAllCost() : this.getSomeCost();
    },
    headerStyle({row, column, rowIndex, columnIndex}) {
      return 'tableStyle'
    },
    headerCellStyle({row, column, rowIndex, columnIndex}) {
      return {'text-align': 'center'};
    },
    popoverHide() {
      this.loading = true;
      setTimeout(() => {
        this.tableColumn2 = this.tableColumn.filter(col => col.show);
        // this.$refs.yearTable.doLayout();
        this.loading = false;
      }, 500);
      localStorage.setItem("analysisTableSetting", JSON.stringify(this.tableColumn));
    },
    deptChange(value) {
      this.deptList.forEach(department => {
        if (department.deptId == value) {
          this.teamList = department.children;
          if (this.teamList.length === 0) {
            this.form.teamId = null;
          } else {
            this.form.teamId = this.teamList[0].teamId;
          }
        }
      });
      this.activeNameMain === 'all' ? this.getAllCost() : this.getSomeCost();
    },
    teamChange() {
      this.activeNameMain === 'all' ? this.getAllCost() : this.getSomeCost();
    },
    yearChange() {
      // 年份切换，重新获取表中部门结构，可能部门/项目组不存在，初始化为0
      this.form.deptId = 0;
      this.teamList = [];
      this.form.teamId = 0;
      this.getDeptList();
      this.activeNameMain === 'all' ? this.getAllCost() : this.getSomeCost();
    },
    getDeptList() {
      axios.get('../module/getDeptList', {
        params: {
          year: this.form.year
        }
      }).then(res => {
        this.deptList = res.data;
        this.deptList.unshift({
          deptId: 0,
          deptName: '全部生产部门',
          children: []
        });
        // this.teamList = [];
      }).catch(error => {
        console.log(error);
      });
    },
    getSomeCost() {
      let param = {
        deptId: this.form.deptId,
        teamId: this.form.teamId,
        year: this.form.year,
        month: this.activeName
      };
      axios.post('../module/getSomeCost', param).then(res => {
        console.log(res.data);
        this.monthlyData = res.data;
      }).catch(error => {
        console.log(error);
      });
    },
    getAllCost() {
      let param = {
        deptId: this.form.deptId,
        teamId: this.form.teamId,
        year: this.form.year,
      };
      axios.post('../module/getAllCost', param).then(res => {
        console.log(res.data);
        this.yearData = res.data;
      }).catch(error => {
        console.log(error);
      });
    },
    resizeHeight() {
      this.tableHeight = window.innerHeight - 260 + 'px';
      this.tableHeight2 = window.innerHeight - 314 + 'px';
    }

  },
  computed: {},
  watch: {
    /*'tr': {
        handler: function (val) { // 此处注意，handler函数不能为箭头函数，this会取上下文，而不是组件里的this,此外，深度监听，必须为handler函数名，否则会无效果
            this.loading = true;
            setTimeout(() => {
                this.loading = false;
            }, 1000);
        },
        deep: true
    }*/
  },
  beforeUpdate() {
    //在数据加载完，重新渲染表格 当Table或其祖先元素由隐藏切换为显示时使用doLayout方法 对Table进行重新布局。解决使用v-if时表头跳动已经行数太多卡顿的问题
    this.$nextTick(() => {
      this.activeNameMain === 'all' ? this.$refs.yearTable.doLayout() : this.$refs.monthlyTable.doLayout()
    })
  },
  mounted() {
    this.tableColumn = localStorage.getItem("analysisTableSetting") ? JSON.parse(localStorage.getItem("analysisTableSetting")) : Object.assign({}, tableColumn);
    setTimeout(() => {
      this.loading = false;
      this.getDeptList();
      this.getAllCost();
    }, 1000);
  }
});

window.onresize = () => {
  vm.resizeHeight();
};