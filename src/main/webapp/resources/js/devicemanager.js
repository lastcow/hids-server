/**
 * Created with IntelliJ IDEA.
 * User: lastcow
 * Date: 3/20/13
 * Time: 6:50 PM
 * To change this template use File | Settings | File Templates.
 */


function initComponent(){

    $.template.init();

    // Datatable
    var tblDevice = $('#tblDevice');

    if($('#deviceCnt').val() == '0'){
        // Remove the empty tr.
        $('#tblDevice tbody tr').remove();
    }

    tblDevice.dataTable({

        'sPaginationType': 'full_numbers',
        'sDom': '<"dataTables_header"lfr>t<"dataTables_footer"ip>',
        'oLanguage' :{
            sZeroRecords: 'No device found in system',
            sInfoFiltered: '(filtered)',
            oPaginate: {
                sFirst: '<<',
                sPrevious: '<',
                sNext: '>',
                sLast: '>>'
            }
        },
        'fnInitComplete': function( oSettings )
        {
            // Style length select
            tblDevice.closest('.dataTables_wrapper').find('.dataTables_length select').addClass('select blue-gradient glossy').styleSelect();
            tableStyled = true;
        }
    });

//    initTblInstalledApps();
//    initTblRunningApps();


}

function initTblInstalledApps(){

    var tblInstalledApps = $('#tblInstalledApps');
    if($('#installedAppsCnt').val() == '0'){
        $('#tblInstalledApps tbody tr').remove();
    }

    tblInstalledApps.dataTable({

        'sPaginationType': 'full_numbers',
        'sDom': '<"dataTables_header"lfr>t<"dataTables_footer"ip>',
        'aoColumnDefs': [
            { 'bSortable': false, 'aTargets': [ 3 ] }
        ],
        'oLanguage' :{
            sZeroRecords: 'No application installed on this device',
            sInfoFiltered: '(filtered)',
            oPaginate: {
                sFirst: '<<',
                sPrevious: '<',
                sNext: '>',
                sLast: '>>'
            }
        },
        'fnInitComplete': function( oSettings )
        {
            // Style length select
            tblInstalledApps.closest('.dataTables_wrapper').find('.dataTables_length select').addClass('select blue-gradient glossy').styleSelect();
            tableStyled = true;
        }
    });
}

function initTblRunningApps(){

    var tblRunningApps = $('#tblRunningApps');
    if($('#runningAppsCnt').val() == '0'){
        $('#tblRunningApps tbody tr').remove();
    }

    tblRunningApps.dataTable({

        'sPaginationType': 'full_numbers',
        'sDom': '<"dataTables_header"lfr>t<"dataTables_footer"ip>',
        'oLanguage' :{
            sZeroRecords: 'No application running on this device',
            sInfoFiltered: '(filtered)',
            oPaginate: {
                sFirst: '<<',
                sPrevious: '<',
                sNext: '>',
                sLast: '>>'
            }
        },
        'fnInitComplete': function( oSettings )
        {
            // Style length select
            tblRunningApps.closest('.dataTables_wrapper').find('.dataTables_length select').addClass('select blue-gradient glossy').styleSelect();
            tableStyled = true;
        }
    });

}

/**
 * Get device information from DB.
 * @param data
 * @param deviceSerial
 */
function getDeviceDetail(data, deviceSerial){
    if(data.status == "begin"){
        // Loading...
    }else if(data.status == "success" ){
        // Set device serial on page
        $('#selectedDeviceSerial').val(deviceSerial);

        // Init table style
        initTblInstalledApps();
        initTblRunningApps();

        // Show div
        $('#details-title').show();
        $('#divDeviceDetails').show();
    }else if(data.status == "complete"){

    }
}

/**
 * Prepare scan app data and submit request.
 * @param data
 * @param appId
 */
function prepareScanAppData(data, appId){
    if(data.status == "begin"){
        // Loading...
    }else if(data.status == "success"){

        $('#selectedAppId').val(appId);
        // Submit the scan request.
        $('#submitAppScan').click();
    }else if(data.status == "complete"){
        // Complete
    }
}

/**
 * Refresh data table.
 * @param data
 */
function refreshDeviceDetail(data){
    if(data.status == "begin"){
        // Loading...
    }else if(data.status == "success"){
        // Init table style
        initTblInstalledApps();
        initTblRunningApps();
    }else if(data.status == "complete"){
        // Complete.
    }
}

