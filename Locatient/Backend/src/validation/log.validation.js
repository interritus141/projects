const joi = require("joi");

const logValidation = (data) => {
  const logValidator = joi.object({
    qrhash: joi.string().max(80).required(),
    roomName: joi.string().min(2).max(50).required(),
    level: joi.number().required(),
    date: joi.date().iso().required(),
  });
  return logValidator.validate(data);
};

module.exports = {
  logValidation,
};
