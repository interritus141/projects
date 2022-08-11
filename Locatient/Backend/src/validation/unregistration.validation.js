const joi = require("joi");

const unregistrationValidation = (data) => {
  const unregistrationValidator = joi.object({
    nhsid: joi.string().min(4).max(50).required(),
    type: joi.string().valid("user", "patient").required(),
  });
  return unregistrationValidator.validate(data);
};

module.exports = {
  unregistrationValidation,
};
