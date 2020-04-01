import 'babel-polyfill';
import { readFile } from 'fs';
import { resolve as pathResolve } from 'path';
import express from 'express';
import React from 'react';
import { renderToString } from 'react-dom/server';
import { StaticRouter, matchPath } from 'react-router';
import bodyParser from 'body-parser';
import cookieParser from 'cookie-parser';

import App from './client/app.jsx';
import routes from './client/routes';

let app = express();

app.disable('x-powered-by');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());

app.use(express.static('public'));

app.get('/logout', (req, res) => {
    res.clearCookie('session');
    res.redirect('/login');
})

function checkRoute(req, res, route) {
    return new Promise((resolve) => {
        const { session } = req.cookies;
        if(route.loggedIn) {
            const { session } = req.cookies;
            if(session !== undefined) {
                resolve(true);
            } else {
                res.redirect('/login');
            }
        } else {
            if(session === undefined) {
                resolve(false);
            } else {
                res.redirect('/');
            }
        }
    });
}

app.get('*', (req, res) => {
    const route = routes.find(route => matchPath(req.url, route));
    checkRoute(req, res, route).then(auth => {
        const pageContent = renderToString(
            <StaticRouter location={req.url}>
                <App auth={auth} />
            </StaticRouter>
        );
        readFile(pathResolve('./src/views/index.html'), 'utf8', (err, index) => {
            if(err) {
                console.log(err);
            } else {
                res.send(index.replace('<div id="root"></div>', '<div id="root">' + pageContent + '</div>'));
            }
        });
    });
});

const PORT = 8080;
app.listen(PORT, () => {
    console.log('Web server running on port ' + PORT);
});