<!-- Script that manipulates the page. -->

$( document ).ready( function (){
    $( "#tabs" ).tabs(  );

    $( "#indexerForm" ).submit( function( event ) {
        event.preventDefault();

        var indexData = {
            url: $( "#urlInput" ).val(),
            contents: $( "#contentInput" ).val()
        };

        $.ajax({
            type: "POST",
            url: "cyberminer/index",
            data: indexData,
            success: function( data ) {
                $( "#urlInput" ).val( "" );
                $( "#contentInput" ).val( "" );
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
                $( "#resultsTable").dataTable().fnClearTable();

                for( url in data.content ) {
                    console.log( "Adding result. " + url + ":" + data.content[url] );
                    $( "#resultsTable").dataTable().fnAddData( {
                        0: url,
                        1: data.content[url]
                    } );
                }
            },
            dataType: "json"
        });
    });

    $( "#resultsTable" ).dataTable( {
        "bFilter": false,
        "bJQueryUI": true,
        "bPaginate": false
    });

    var searchField = $( "#searchText" );
    searchField.keydown( function( event ) {
        if( event.keyCode == 13 ) {
            $( "#searchButton" ).click();
        }
    } );

    searchField.focus();
});