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