module.exports = function override(config, env) {
    if (env === 'development') {
        const devServer = config.devServer || {};
        devServer.setupMiddlewares = (middlewares, devServer) => {
            if (!devServer) {
                throw new Error('webpack-dev-server is not defined');
            }

            // Add custom middleware logic here if needed
            return middlewares;
        };
        config.devServer = devServer;
    }

    return config;
};
