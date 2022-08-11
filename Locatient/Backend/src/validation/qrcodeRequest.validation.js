const joi = require("joi");

const qrcodeRequestValidation = (data) => {
  const qrcodeRequestValidator = joi.object({
    nhsid: joi.string().min(4).max(50).required(),
  });
  return qrcodeRequestValidator.validate(data);
};

module.exports = {
  qrcodeRequestValidation,
};
