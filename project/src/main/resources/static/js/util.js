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

function strToDate(dateStr) {
  if (dateStr) {
      const dateStrArr = dateStr.split(' ')
    if (dateStrArr.length === 2) { // 包含时间
      const date = dateStrArr[0].split('/')
      const time = dateStrArr[1].split(':')
      return new Date(date[0], date[1] * 1 - 1, date[2], time[0], time[1], time[2])
    }
    if (dateStrArr.length === 1) { // 只有日期
      const date = dateStrArr[0].split('/')
      return new Date(date[0], date[1] * 1 - 1, date[2])
    }
  }
}

// 获取月初
function getMonthFirstDay(date) {
  let first
  if (date === undefined) {
    const now = new Date()
    first = new Date(now.getFullYear(), now.getMonth(), 1) // 上月初
  } else {
    if (typeof date === 'string') {
      if (date.length === 7) { // 月份
        date += '/01'
      }
      const now = strToDate(date)
      first = new Date(now.getFullYear(), now.getMonth(), 1) // 上月初
    } else {
      first = new (date.getFullYear(), date.getMonth(), 1) // 月初
    }
  }
  let month = first.getMonth() + 1
  if (month.toString().length <= 1) {
    month = '0' + month.toString()
  }
  return first.getFullYear() + '/' + month + '/01'
}

// 获取月末
function getMonthLastDay(date) {
  let first
  if (date === undefined) {
    const now = new Date()
    first = new Date(now.getFullYear(), now.getMonth(), 1) // 上月初
  } else  {
    if (typeof date === 'string') {
      if (date.length === 7) { // 月份
        date += '/01'
      }
      const now = strToDate(date)
      first = new Date(now.getFullYear(), now.getMonth() + 1, 1) // 上月初
    } else {
      first = new (date.getFullYear(), date.getMonth() + 1, 1) // 月初
      }
  }
  const last = new Date(first.getTime() - 1)
  let month = last.getMonth() + 1
  if (month.toString().length <= 1) {
    month = '0' + month.toString()
  }
  return last.getFullYear() + '/' + month + '/' + last.getDate()
}