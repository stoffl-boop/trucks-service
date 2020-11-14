$('#register_form').submit(function (event) {

    event.preventDefault();

    var $form = $(this),
        url = $form.attr('action'),
        data = new FormData($form[0]);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: url,
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        success: function (data) {
            renderMessage(data);
        }
    });

    function renderMessage(message) {
        $form.remove();
        var div = document.createElement('div');
        div.innerHTML = `
            <div class="card-body px-lg-5 pt-0">
                <form class="text-center border border-light pt-3 pb-3">
                    <div class="col">
                        <p>${message}</p>
                    </div>
                    <a class="btn btn btn-success mb-15" href="/" role="button">На головну</a>
                </form>
            </div>
        `;
        $('#card').append(div);
    }
});