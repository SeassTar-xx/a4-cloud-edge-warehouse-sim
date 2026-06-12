declare module "*.vue" {
  import Vue from "vue";

  interface YourComponentName extends Vue {
    input: string; // 假设 input 是一个字符串类型的属性
  }

  export default YourComponentName;
}