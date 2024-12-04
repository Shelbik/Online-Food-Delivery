const webpack = require('webpack');

module.exports = {
  webpack: {
    configure: (webpackConfig) => {
      // Добавление полифилов для process и os
      webpackConfig.resolve.fallback = {
        process: require.resolve('process/browser.js'), // Указание расширения .js
        os: require.resolve('os-browserify/browser.js'), // Указание расширения .js
      };

      // Добавление плагина ProvidePlugin для автоматического предоставления process
      webpackConfig.plugins = [
        ...webpackConfig.plugins,
        new webpack.ProvidePlugin({
          process: 'process/browser.js', // Указание расширения .js
        }),
      ];

      return webpackConfig;
    },
  },
};
