const router = require("express").Router();
const { User } = require("../models/model");
const jwt = require("jsonwebtoken");
const { userLoginTokenExpire } = require("../configs/server.config");

const { loginValidation } = require("../validation/auth.validation");

router.get("/test/", (req, res) => {
  return res.status("200").json({
    message: "Connection successful",
  });
});

router.post("/login/", async (req, res) => {
  const { error } = loginValidation(req.body);
  if (error) {
    return res.status("400").json({ error: error.details[0].message });
  }
  User.findOne({ nhsid: req.body.nhsid }, (err, user) => {
    if (err) {
      res.status("400").json({ error: err });
    } else if (!user) {
      res.status("401").json({ error: "User not found" });
    } else {
      user.verifyPassword(req.body.password, (err, matched) => {
        if (err) {
          res.status("400").json({ error: "Failed to verify password" });
        } else if (matched) {
          const jsonTokenObj = { _id: user._id, nhsid: user.nhsid };
          const signedToken = jwt.sign(jsonTokenObj, process.env.PASSPORT_KEY, {
            expiresIn: userLoginTokenExpire,
          });
          res
            .status("200")
            .json({ message: "Login Verified", user, signedToken });
        } else {
          res.status("401").json({ error: "Password incorrect" });
        }
      });
    }
  });
});

module.exports = router;
