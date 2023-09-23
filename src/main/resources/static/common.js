toastr.options = {
    closeButton: true,
    debug: false,
    newestOnTop: true,
    progressBar: true,
    positionClass: "toast-top-right",
    preventDuplicates: false,
    onclick: null,
    showDuration: "300",
    hideDuration: "1000",
    timeOut: "4000",
    extendedTimeOut: "1000",
    showEasing: "swing",
    hideEasing: "linear",
    showMethod: "fadeIn",
    hideMethod: "fadeOut"
};

// toastr 에러 메시지를 표시하는 함수
function showError(message) {
    toastr.error(message, '에러');
}

// toastr 성공 메시지를 표시하는 함수
function showSuccess(message) {
    toastr.success(message, '성공');
}

// 양식을 유효성 검사하고 적절한 메시지를 표시하는 함수
function validateForm() {
    var title = document.querySelector("input[name='subject-find']").value; // 'input[type="text"]'
    var content = document.querySelector('textarea').value;         // 'textarea'

    if (!title && !content) {
        showError('제목과 내용을 입력하세요.');
    } else if (!title) {
        showError('제목을 입력하세요.');
    } else if (!content) {
        showError('내용을 입력하세요.');
    } else {
        // 양식이 유효하면 필요한 경우 여기에서 제출할 수 있습니다
        showSuccess('작성이 완료되었습니다.');
    }
}

// 양식 제출 이벤트에 validateForm 함수를 연결합니다
document.querySelector('.write-form').addEventListener('submit', function (event) {
    event.preventDefault(); // 기본 양식 제출 방지
    validateForm();
});