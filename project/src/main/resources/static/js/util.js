const DATA_INTEGRITY_ERROR_TIP = "数据完整性异常，请先删除依赖"

function dateToStr(date) {
  if (date instanceof Date) {
    let month = date.getMonth() + 1
    if (month < 10) {
      month = '0' + month
    }
    let day = date.getDate()
    if (day < 10) {
      day = '0' + day
    }
    return date.getFullYear() + '/' + month + '/' + day
  } else {
    return date
  }
}
