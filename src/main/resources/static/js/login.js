const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

document.getElementById('signUpForm').addEventListener('submit', async function (event) {
    event.preventDefault();
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    const requestData = {
        email: email,
        password: password
    };

    try {
        const response = await fetch('/api/auth/register', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(requestData)
        });

        if (response.ok) {
            const message = await response.text();
            alert(message);
        } else {
            alert('Failed to register');
        }
    } catch (error) {
        console.error("Error: ", error);
        alert('An error occurred!');
    }
});

// document.getElementById('loginForm').addEventListener('submit', async function (event) {
//     event.preventDefault();
//     const email = document.getElementById('loginEmail').value;
//     const password = document.getElementById('loginPassword').value;
//
//     const requestData = {
//         email: email,
//         password: password
//     };
//
//     try {
//         const response = await fetch('/api/auth/login', {
//             method: 'POST',
//             headers: {'Content-Type': 'application/json'},
//             body: JSON.stringify(requestData)
//         });
//
//         if (response.ok) {
//             const message = await response.text();
//             console.log(message);
//             window.location.href = "/user"; // Перенаправление на страницу пользователя
//         } else {
//             const errorMessage = await response.text();
//             alert(errorMessage);
//         }
//     } catch (error) {
//         console.error("Error: ", error);
//         alert('An error occurred!');
//     }
// });


signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
});
