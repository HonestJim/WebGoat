$(document).ready(function () {
//    $("#postReview").on("click", function () {
//        var commentInput = $("#reviewInput").val();
//        $.ajax({
//            type: 'POST',
//            url: 'csrf/review',
//            data: JSON.stringify({text: commentInput}),
//            contentType: "application/json",
//            dataType: 'json'
//        }).then(
//            function () {
//                getChallenges();
//                $("#commentInput").val('');
//            }
//        )
//    });

    var html = '<li class="comment">' +
        '<div class="pull-left">' +
        '<img class="avatar" src="images/avatar1.png" alt="avatar"/>' +
        '</div>' +
        '<div class="comment-body">' +
        '<div class="comment-heading">' +
        '<h4 class="user">USER / STARS stars</h4>' +
        '<h5 class="time">DATETIME</h5>' +
        '</div>' +
        '<p>COMMENT</p>' +
        '</div>' +
        '</li>';

    getChallenges();

    function getChallenges() {
        $("#list").empty();
        $.get('csrf/review', function (result, status) {
            for (var i = 0; i < result.length; i++) {
                var safeUser = escapeHtml(result[i].user);
                var safeDate = escapeHtml(result[i].dateTime);
                var safeComment = escapeHtml(result[i].text);
                var safeStars = escapeHtml(result[i].stars);
                var comment = html.replace('USER', safeUser);
                comment = comment.replace('DATETIME', safeDate);
                comment = comment.replace('COMMENT', safeComment);
                comment = comment.replace('STARS', safeStars)
                $("#list").append(comment);
            }

        });
    }
})