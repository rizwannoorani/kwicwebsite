<!-- Script that manipulates the page. -->

$( document ).ready( function (){
    $( "#tabs" ).tabs(  );

    $( "#indexerForm" ).submit( function( event ) {
        event.preventDefault();

        //do ajax call here and display submission status
    });

    $( "#resultsTable").dataTable( {
        "bFilter": false,
        "bJQueryUI": true,
        "bPaginate": false
    });
});