const express = require('express');
const jwt = require('jsonwebtoken');
const loki = require('lokijs');
const db = new loki('content.json');
const accounts = db.addCollection('accounts');
const app = express();
const bodyParser = require("body-parser");
const secret_key = "spiralworksdemo";
const { check, validationResult } = require('express-validator/check');

app.use(express.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.listen(3000, function () { console.log('App listening on port 3000!'); });

app.get('/', function (req, res) {
    res.send('Hello World');
})

app.post('/api/login', function (req, res) {
    const result = accounts.findOne({ '$and': [{ 'email': req.body.email }, { 'password': req.body.password }] })
    if (result !== null) {
        const payload = result.email + ":" + result.password;
        jwt.sign({ payload }, secret_key, (err, token) => {
            res.json({
                result: 'success',
                token_type: 'Bearer',
                token: token
            });
        });
    } else {
        res.sendStatus(403)
    }
});

app.post('/api/signup',
    [check('email').isEmail(), check('password').isLength({ min: 8 })],
    verifyEmail,
    function (req, res) {

        const errors = validationResult(req);

        if (!errors.isEmpty()) {
            return res.status(422).json({ errors: errors.array() });
        }

        const payload = req.body.email + ":" + req.body.password
        const token = jwt.sign({ payload }, secret_key);

        const user = {
            email: req.body.email,
            first_name: req.body.first_name,
            last_name: req.body.last_name,
            password: req.body.password
        }

        accounts.insert(user);

        console.log('New account created: '+ user.email + ' | '+ user.password);
        res.json({
            result: 'success',
            token_type: 'Bearer',
            token: token
        });
    });

app.get('/api/check-email' , function (req, res) {

    var result = accounts.findOne({ 'email': req.query.email })
    if (result == null) {
        res.json({
            result: 'success',
            message: 'Email is available for use'
        })
    } else {
        res.sendStatus(403)
    }
});

app.get('/api/me', verifyToken, function (req, res) {
    jwt.verify(req.token, secret_key, function (err, data) {
        const email = data.payload.split(":")[0];
        const result = accounts.findOne({ 'email': email });
        if (err) {
            res.sendStatus(403);
        } else {
            res.json({
                first_name: result.first_name,
                last_name: result.last_name,
                email: result.email
            });
        }
    });
});


function verifyEmail(req, res, next) {
    var result = accounts.findOne({ 'email': req.body.email })
    if (result == null) {
        next();
    } else {
        res.json({
            result: 'error',
            message: 'Email is already taken'
        })
    }
}

function verifyToken(req, res, next) {
    const bearerHeader = req.headers["authorization"];
    if (typeof bearerHeader !== 'undefined') {
        const bearer = bearerHeader.split(" ");
        const bearerToken = bearer[1];
        req.token = bearerToken;
        next();
    } else {
        res.sendStatus(403);
    }
}

