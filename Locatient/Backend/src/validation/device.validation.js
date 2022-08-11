const joi = require("joi");

const deviceValidation = (data) => {
  const deviceValidator = joi.object({
    name: joi.string().min(2).max(50).required(),
    apiKey: joi.string().min(2).max(50).required(),
  });
  return deviceValidator.validate(data);
};

module.exports = {
  deviceValidation,
};
