const jwtStrategy = require("passport-jwt").Strategy;
const extractJWT = require("passport-jwt").ExtractJwt;
const { ExtractJwt } = require("passport-jwt/lib");
const { User, Device } = require("../models/model");
const HeaderAPIKeyStrategy =
  require("passport-headerapikey").HeaderAPIKeyStrategy;

module.exports = (passport) => {
  let options = {};
  options.jwtFromRequest = extractJWT.fromAuthHeaderAsBearerToken("jwt");
  options.secretOrKey = process.env.PASSPORT_KEY;
  passport.use(
    new jwtStrategy(options, function (payload, done) {
      User.findOne({ _id: payload._id }, (err, user) => {
        if (err) {
          return done(err, false);
        }
        if (user) {
          return done(null, user);
        } else {
          return done(null, false);
        }
      });
    })
  );
  passport.use(
    new HeaderAPIKeyStrategy({ header: "Authorization" }, false, function (
      apikey,
      done
    ) {
      Device.findOne({ apiKey: apikey }, function (err, device) {
        if (err) {
          return done(err);
        }
        if (!device) {
          return done(null, false);
        }
        return done(null, device);
      });
    })
  );
};
