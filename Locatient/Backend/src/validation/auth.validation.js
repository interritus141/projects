const joi = require("joi");

const registrationValidation = (data) => {
  const registrationValidator = joi.object({
    nhsid: joi.string().min(4).max(50).required(),
    password: joi.string().min(8).max(1024).required(),
    privilege: joi.string().required().valid("user", "admin"),
  });
  return registrationValidator.validate(data);
};

const loginValidation = (data) => {
  const loginValidator = joi.object({
    nhsid: joi.string().min(4).max(50).required(),
    password: joi.string().min(8).max(1024).required(),
  });
  return loginValidator.validate(data);
};

module.exports = {
  registrationValidation,
  loginValidation,
};
