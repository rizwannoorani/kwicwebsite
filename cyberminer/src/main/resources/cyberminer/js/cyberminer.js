<!-- Script that manipulates the page. -->

$( document ).ready( function (){
    $( "#tabs" ).tabs(  );

    $( "#indexerForm" ).submit( function( event ) {
        event.preventDefault();

        //do ajax call here and display submission status
//        $.post( "cyberminer/index", function( data ) {
//            alert( data.content );
//        } );

        var indexData = {
            url: $( "#urlInput" ).val(),
            contents: $( "#contentInput" ).val()
        };

        $.ajax({
            type: "POST",
            url: "cyberminer/index",
            data: indexData,
            success: function( data ) {
                alert( "Successful post! " + data.content );
            },
            dataType: "json"
        });
    });

    $( "#resultsTable").dataTable( {
        "bFilter": false,
        "bJQueryUI": true,
        "bPaginate": false
    });
});