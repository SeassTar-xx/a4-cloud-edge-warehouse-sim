const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  //改变端口
    transpileDependencies: true,
    devServer:{
      port:7000
  },
  //改变标题
  chainWebpack: config =>{
      config.plugin('html')
      .tap(args=>{
        args[0].title="徐歆好帅啊";
        return args;
    })
  }
})
