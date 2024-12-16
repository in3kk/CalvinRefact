window.onload = function(){
    let subject1 = document.getElementById('typeJS');
    if(subject1.value != ''){
        subjectTypeChanged(subject1.value);
    }
}
function subjectTypeChanged(subjectType){
    $.ajax( "/subjectFieldList",
        {
          method: 'get',
          data : { subject_type: subjectType },
          dataType: 'json'
        }
      )
      .done(function(result) { // 서버요청이 성공시의 콜백함수
        const select = document.getElementById('subject_field');
        let subject = document.getElementById('fieldJS').value;;
        select.innerHTML = '';
        if(subject != null){
            for(let i = 0; i < result.length; i++){
                let option = document.createElement("option");
                option.value = result[i];
                option.textContent = result[i];
                if (subject == result[i]) {
                    option.selected = true;
                }
                select.appendChild(option);
            }
        }else{
            select.innerHTML = '<option selected style="display:none">분류를 선택해 주세요</option>';
            for(let i = 0; i < result.length; i++){
                let option = document.createElement("option");
                option.value = result[i];
                option.textContent = result[i];
                select.appendChild(option);
            }
        }

      })
      .fail(function() { // 서버요청이 에러시의 콜백함수
        const select = document.getElementById('subject_field');
        select.innerHTML = '<option selected>오류 발생 잠시후 다시 시도해 주세요</option>';
      })
      .always(function() {

      });
}