var express = require('express');
var router = express.Router();
var passport=require('passport');
/* GET home page. */
router.get('/', function(req, res) {
  passport.authenticate('google',{scope: 'https://www.google.com/m8/feeds'});
  res.render('index', { title: 'Address book' });

});

module.exports = router;
