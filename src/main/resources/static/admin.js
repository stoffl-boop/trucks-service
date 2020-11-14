function changeTaxiDriverStatus(url, driverId) {
    var posting = $.post(
        url,
        {
            driverId: driverId
        });

    posting.done(function (data) {
        $('#' + driverId).parent().remove();
        $('#drv-' + driverId).each(function () {
            $(this).load(' #drv-' + driverId, function () {
               $(this).children().unwrap();

            });
        });
    });
}

function modalWindowAssignEventProcessing() {
    $('#truck-drivers-container').on('click', '.transportAssign', function(e) {
        $('#assignAutoTableData').load(' #assignAutoTableData', function() {
            $(this).children().unwrap();
            $('#assignAutoTable').DataTable();
            $('.assignTransportPostButton').attr('id', e.target.getAttribute('drv-id'));
        });
    })
}

function assignAuto(url, driverId, truckId) {
     var posting = $.post(
         url,
         {
             driverId: driverId,
             truckId: truckId
         });

     posting.done(function (data) {
         $('#drvTruck-' + driverId).each(function () {
             $(this).load(' #drvTruck-' + driverId, function () {
                $(this).children().unwrap();
             });
         });
         $('.assign-auto-modal').empty();
         $('.assign-auto-modal').append('<p>' + data + '</p>');
     });
 }

 function assignDriver(url, orderId, driverId) {
     var posting = $.post(
         url,
         {
             driverId: driverId,
             orderId: orderId
         });

     posting.done(function (data) {
         $('#order-' + orderId).each(function () {
             $(this).load(' #order-' + orderId, function () {
                $(this).children().unwrap();
             });
         });
         $('.assign-driver-modal').empty();
         $('.assign-driver-modal').append('<p>' + data + '</p>');
     });
 }

function deleteResourceEntity(url, id) {
    var posting = $.post(
        url,
        {
            id: id
        });

    posting.done(function (data) {
        $('#truck-drivers-container').load(' #truck-drivers-container', function() {
            $(this).children().unwrap();
            $('#dtBasicExample').DataTable();
            modalWindowAssignEventProcessing();
        });
    });
}

$(document).ready(function () {
    var assignTable = $('#dtBasicExample').DataTable();
    modalWindowAssignEventProcessing();

    $('#assignAutoTable').DataTable();
    $('.dataTables_length').addClass('bs-select');
});

