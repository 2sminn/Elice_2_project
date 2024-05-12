// Import the functions you need from the SDKs you need
import { initializeApp } from "https://www.gstatic.com/firebasejs/10.11.0/firebase-app.js";
import { getAnalytics } from "https://www.gstatic.com/firebasejs/10.11.0/firebase-analytics.js";
import { getStorage, ref, uploadBytesResumable, getDownloadURL } from 'https://www.gstatic.com/firebasejs/10.11.0/firebase-storage.js';
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
    apiKey: "AIzaSyB7zwpGhKiLAXuuxMgmDiTasBYCtKdSGlw",
    authDomain: "save-me-abandoned-animal.firebaseapp.com",
    projectId: "save-me-abandoned-animal",
    storageBucket: "save-me-abandoned-animal.appspot.com",
    messagingSenderId: "887680431681",
    appId: "1:887680431681:web:6386491c67bb9d455038c0",
    measurementId: "G-T8GZCF1ZSS"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);
const storage = getStorage(app);

$(document).ready(function() {
    // CKEditor 초기화
    ClassicEditor
        .create(document.querySelector('#content'), {
            extraPlugins: [ MyCustomUploadAdapterPlugin ],
        })
        .then(editor => {
            window.editor = editor;
        })
        .catch(error => {
            console.error(error);
        });

    // 작성완료 버튼 클릭 이벤트 핸들러
    $("#submitBtn").click(function() {
        var title = $("#title").val().trim(); // 제목의 앞뒤 공백 제거

        // 제목이 비어있거나 공백만 있으면 경고 메시지를 표시하고 함수를 종료
        if (title === '') {
            alert('게시글 제목을 입력해주세요!');
            $("#title").focus(); // 입력 필드에 포커스 주기
            return; // 이후의 코드 실행을 중단
        }

        var postData = {
            title: title,
            boardType: $("#boardType").val(),
            content: window.editor.getData() // CKEditor에서 데이터 가져오기
        };

        $.ajax({
            type: "POST",
            url: "/api/community/post", // 서버의 API 경로
            data: JSON.stringify(postData),
            contentType: "application/json; charset=utf-8",
            success: function(response) {
                alert("게시글이 성공적으로 등록되었습니다.");
                window.location.href = "/communities";
            },
            error: function(xhr, status, error) {
                alert("게시글 등록에 실패하였습니다: " + xhr.responseText);
            }
        });
    });
});

// CKEditor 커스텀 업로드 어댑터 플러그인 구현
function MyCustomUploadAdapterPlugin(editor) {
    editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
        return new FirebaseUploadAdapter(loader);
    };
}

class FirebaseUploadAdapter {
    constructor(loader) {
        this.loader = loader;
    }

    upload() {
        return this.loader.file.then(file => new Promise((resolve, reject) => {
            // 이미지 객체를 생성
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = (e) => {
                const img = new Image();
                img.src = e.target.result;
                img.onload = () => {
                    // Canvas 를 얻어와서 Canvas 를 통해 이미지 크기 조정
                    const canvas = document.createElement('canvas');
                    const ctx = canvas.getContext('2d');
                    const MAX_WIDTH = 800;
                    const MAX_HEIGHT = 600;
                    let width = img.width;
                    let height = img.height;

                    // 너비와 높이 비율 유지
                    if (width > height) {
                        if (width > MAX_WIDTH) {
                            height *= MAX_WIDTH / width;
                            width = MAX_WIDTH;
                        }
                    } else {
                        if (height > MAX_HEIGHT) {
                            width *= MAX_HEIGHT / height;
                            height = MAX_HEIGHT;
                        }
                    }

                    canvas.width = width;
                    canvas.height = height;
                    ctx.drawImage(img, 0, 0, width, height);

                    // 조정된 이미지를 Blob 형태로 변환
                    canvas.toBlob(function(blob) {
                        // 현재 날짜와 시간으로 파일 이름 생성
                        const timestamp = new Date().getTime();
                        const fileExtension = file.name.split('.').pop();
                        const newFileName = `${timestamp}-${file.name.split('.')[0]}.${fileExtension}`;

                        // 새로운 파일 객체 생성
                        const newFile = new File([blob], newFileName, {
                            type: 'image/jpeg',
                            lastModified: Date.now()
                        });

                        // 현재 날짜 정보 가져오기
                        const now = new Date();
                        const year = now.getFullYear();
                        const month = (now.getMonth() + 1).toString().padStart(2, '0');
                        const day = now.getDate().toString().padStart(2, '0');

                        // 날짜별 폴더 구조를 반영한 파일 경로 생성
                        const filePath = `images/${year}/${month}/${day}/${newFileName}`;

                        // 올바른 storage ref 사용
                        const storageRef = ref(storage, filePath);
                        const uploadTask = uploadBytesResumable(storageRef, newFile);

                        uploadTask.on('state_changed',
                            (snapshot) => {
                                // 업로드 진행 상태를 여기에 표시
                            },
                            (error) => {
                                reject('업로드 중 에러 발생: ', error);
                            },
                            () => {
                                getDownloadURL(uploadTask.snapshot.ref).then((downloadURL) => {
                                    resolve({
                                        default: downloadURL
                                    });
                                });
                            }
                        );
                    }, 'image/jpeg');
                };
            };
        }));
    }
}