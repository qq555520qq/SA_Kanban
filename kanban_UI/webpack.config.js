const path = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin')
var webpack = require('webpack');

module.exports = {
    devServer: {
        inline:true,
        port: 3000,
        historyApiFallback: true
    },
    entry: {
      app: './src/index.js'

    },
    
    output: {
    path: path.join(__dirname, '/dist'),
    filename: 'ezKanban/index_bundle.js',
    publicPath: '/'
    },
    module: {
        rules: [
            {
                test: /\.js?$/,
                exclude: /node_modules/,
                use: [
                {
                    loader: 'babel-loader',
                    options: {
                    presets: ['react']
                    }
                }
                ],
            },

            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader']
            },
            {
                test: /\.(png|svg|jpg|gif|jpe?g)$/,
                use: [
                  {
                    options: {
                      name: "[name].[ext]",
                      outputPath: "images/"
                      //outputPath: "ezkanban/"
                    },
                    loader: "file-loader"
                  }
                ]
            }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
        template: './src/index.html'
        })
    ]
}
