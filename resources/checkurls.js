function checkurls() {
    alert('hi')
    urls = document.getElementsByName('checkme')
    urls.forEach(item => console.log(url.href + ' ' + UrlExists(url.href)))
}

function UrlExists(url) {
  var http = new XMLHttpRequest()
  http.open('HEAD', url, false)
  http.send()
  return http.status
}