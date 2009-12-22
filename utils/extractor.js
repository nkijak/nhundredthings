/*
 * This javascript can be used within Firebug to extract values from the weekly pages on www.hundredpushups.com
 * Requires jQuery on the page. Can be "installed" via bookmarklet @ http://www.learningjquery.com/2009/04/better-stronger-safer-jquerify-bookmarklet
 */

var workout = {'easy':[],'mid':[],'hard':[]};

var week = 1;

function cleanMax(value) {
    if (value.indexOf("max") == -1) { return value;}
    var len = 1;
    if (value.indexOf(" ") > -1 && value.length > 16) { len++; }
    value = value.substring(14);
    return value.substring(0,value.length-len)
     
}

function prettyPrint(workout,week,level) {
    var set = "";
    var values = [];
    var day = 1;
    var newline = false;
    for(var i = 1; i < workout.length; i++) {
        if (newline) { 
            set += workoutCode(values,week,day,level)+"\r\n"; 
            values=[]; 
            newline = false;
            day++;
        }
        if (i%5 == 0) {newline = true;}
        
        values.push(workout[i]);
    }
    set += workoutCode(values,week,day,level);
    return set;
}

function workoutCode(values,week,day,level) {
    return "PUSHUPS["+week+"]["+day+"]["+level+"] = new int[]{"+values.join(",")+"};";
}

function getWeek() {
    var path = document.location.pathname;
    var value = path.substring(5,path.indexOf("."));
    return value;
}

// main
week = getWeek();

$('tr [class=specalt],tr [class=spec]').each(function() {
    var easy = $(this).next();
    var mid = easy.next();
    var hard = mid.next();
    workout['easy'].push(cleanMax(easy.html()));
    workout['mid'].push(cleanMax(mid.html()));
    workout['hard'].push(cleanMax(mid.html()));     
});
    console.log(prettyPrint(workout.easy,week,0)+"\r\n"+
    prettyPrint(workout.mid,week,1)+"\r\n"+
    prettyPrint(workout.hard,week,2));

