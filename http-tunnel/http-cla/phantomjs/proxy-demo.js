var webpage = require('webpage');

var page = webpage.create();

page.open("http://proxy.abuyun.com/current-ip", {}, function(status) {
    console.log('>> ' + page.content);

    var page2 = webpage.create();

    page2.onResourceReceived = function(j) {
        for (var i = 0; i < j.headers.length; i++) {
            console.log(j.headers[i].name + ': ' + j.headers[i].value);
        }
    };

    page2.open("http://test.abuyun.com", {}, function(status) {
        console.log('status> ' + status);
        console.log(page.content);

        setTimeout(function() {
            phantom.exit();
        }, 3000);
    });
});
