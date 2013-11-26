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

            },
            dataType: "json"
        });
    });

    $( "#searchButton").click( function( event ) {
        var searchData = {
            queryType: $( "#queryTypeCombo" ).val(),
            queryString: $( "#searchText" ).val()
        };

        $.ajax({
            type: "POST",
            url: "cyberminer/search",
            data: searchData,
            success: function( data ) {

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