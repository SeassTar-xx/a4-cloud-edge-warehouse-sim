<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :xs="24" :md="14">
        <el-card shadow="never">
          <div slot="header">
            <span>算法系统参数</span>
          </div>
          <el-form ref="form" :model="form" :rules="rules" label-width="160px">
            <el-form-item label="当前周期号" prop="currentCycleNum">
              <el-input-number v-model="form.currentCycleNum" :min="1" controls-position="right" />
            </el-form-item>
            <el-form-item label="周期长度" prop="cycleLen">
              <el-input-number v-model="form.cycleLen" :min="1" controls-position="right" />
            </el-form-item>
            <el-form-item label="允许超过订货上限" prop="exceedPossible">
              <el-radio-group v-model="form.exceedPossible">
                <el-radio :label="1">允许</el-radio>
                <el-radio :label="0">不允许</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="批量策略" prop="lotsPrinciple">
              <el-select v-model="form.lotsPrinciple" placeholder="请选择批量策略">
                <el-option label="按净需求" :value="0" />
                <el-option label="按包装量取整" :value="1" />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-check" @click="submitForm" v-hasPermi="['algorithm:systeminfo:edit']">保存</el-button>
              <el-button icon="el-icon-refresh" @click="getInfo">刷新</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :md="10">
        <el-table v-loading="loading" :data="systeminfoList">
          <el-table-column label="当前周期号" align="center" prop="currentCycleNum" />
          <el-table-column label="周期长度" align="center" prop="cycleLen" />
          <el-table-column label="超过上限" align="center" prop="exceedPossible">
            <template slot-scope="scope">
              <el-tag :type="scope.row.exceedPossible === 1 ? 'success' : 'info'">
                {{ scope.row.exceedPossible === 1 ? '允许' : '不允许' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="批量策略" align="center" prop="lotsPrinciple">
            <template slot-scope="scope">
              <span>{{ scope.row.lotsPrinciple === 1 ? '包装量取整' : '净需求' }}</span>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listSysteminfo, getSysteminfo, saveSysteminfo } from "@/api/algorithm/systeminfo";

export default {
  name: "Systeminfo",
  data() {
    return {
      loading: true,
      systeminfoList: [],
      form: {
        currentCycleNum: 1,
        cycleLen: 30,
        exceedPossible: 1,
        lotsPrinciple: 0
      },
      rules: {
        currentCycleNum: [{ required: true, message: "当前周期号不能为空", trigger: "blur" }],
        cycleLen: [{ required: true, message: "周期长度不能为空", trigger: "blur" }]
      }
    };
  },
  created() {
    this.getInfo();
  },
  methods: {
    getInfo() {
      this.loading = true;
      getSysteminfo().then(response => {
        if (response.data) {
          this.form = response.data;
        }
        return listSysteminfo();
      }).then(response => {
        this.systeminfoList = response.rows;
        this.loading = false;
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          saveSysteminfo(this.form).then(() => {
            this.$modal.msgSuccess("保存成功");
            this.getInfo();
          });
        }
      });
    }
  }
};
</script>
