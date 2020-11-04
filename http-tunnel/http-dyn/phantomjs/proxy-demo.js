var webpage = require('webpage');

var page = webpage.create();

page.open("http://test.abuyun.com", {}, function(status) {
    console.log('>> ' + page.content);

    setTimeout(function() {
        phantom.exit();
    }, 3000);
});
