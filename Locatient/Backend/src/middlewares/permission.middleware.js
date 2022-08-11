module.exports = {
  adminPrivilege: (req, res, next) => {
    if (!req.user || !req.user.isAdmin()) {
      res.status("401").json({
        error: "The action you are performing requires higher privilege",
      });
    } else {
      next();
    }
  },
  userPrivilege: (req, res, next) => {
    if (!req.user || (!req.user.isAdmin() && !req.user.isUser())) {
      res.status("401").json({
        error: "The action you are performing requires logging in",
      });
    } else {
      next();
    }
  },
  hasKey: (req, res, next) => {
    if (!req.user) {
      res.status("401").json({
        error: "Invalid api key",
      });
    } else {
      next();
    }
  },
};
