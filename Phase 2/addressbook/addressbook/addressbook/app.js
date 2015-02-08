var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var passport= require('passport');
var debug = require('debug')('my-application');

var google_strategy = require('passport-google-oauth').OAuth2Strategy;

// Database
var mongo = require('mongoskin');
var db = mongo.db("mongodb://localhost:27017/addressbook", {native_parser:true});

var routes = require('./routes/index');
var users = require('./routes/users');

var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');
app.set('port', process.env.PORT || 3000);

// uncomment after placing your favicon in /public
//app.use(favicon(__dirname + '/public/favicon.ico'));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded());
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
app.use(passport.initialize());


// Make our db accessible to our router
app.use(function(req,res,next){
    req.db = db;
    next();
});

app.use('/', routes);
app.use('/users', users);




/// catch 404 and forwarding to error handler
app.use(function(req, res, next) {
    var err = new Error('Not Found');
    err.status = 404;
    next(err);
});

/// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
    app.use(function(err, req, res, next) {
        res.status(err.status || 500);
        res.render('error', {
            message: err.message,
            error: err
        });
    });
}

// production error handler
// no stacktraces leaked to user
app.use(function(err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
        message: err.message,
        error: {}
    });
});


passport.use(new google_strategy({
    clientID: '252571161925-7ta16fgpvljnu4adl2d6v43b4agsue66.apps.googleusercontent.com',
    clientSecret: 'Zr3YoYJl7jJ8LyvrK8OqTcIp',
    callbackURL: 'http://localhost:1337/auth/google/callback'
  },
  function(accessToken, refreshToken, profile, done) {
    UserDB.findOne({email: profile._json.email},function(err,usr) {
        usr.token = accessToken;    
        usr.save(function(err,usr,num) {
            if(err) {
                console.log('error saving token');
            }
        });
        process.nextTick(function() {
            return done(null,profile);
        });
    });
  }
));
passport.authenticate('google',{scope: 'https://www.google.com/m8/feeds'});
var server = app.listen(app.get('port'), function() {
   debug('Express server listening on port ' + server.address().port);
 });

module.exports = app;
module.exports=passport;