const passport = require("passport");

module.exports = function (req, res, next) {
  passport.authenticate("jwt", { session: false }, function (err, user, info) {
    if (!err) {
      req.user = user;
      next();
    } else {
      next(info);
    }
  })(req, res, next);
};
