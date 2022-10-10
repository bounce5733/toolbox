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

/**
 * 获取两个日期之间的月份数组
 * 
 * @param {*} start yyyy/MM/dd
 * @param {*} end yyyy/MM/dd
 */
function getBetweenMonth(start, end) {
  const result = []
  const s = start.split("/")
  const e = end.split("/")
  let min = new Date()
  let max = new Date()
  min.setFullYear(s[0], s[1] - 1, 1) // 开始日期
  max.setFullYear(e[0], e[1] - 1, 1) // 结束日期
  let curr = min
  while (curr <= max) {
    const month = curr.getMonth()
    result.push(dateToStr(curr).substr(0, 7))
    curr.setMonth(month + 1)
  }
  return result
}