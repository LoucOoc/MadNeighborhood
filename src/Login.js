import { useState } from "react";
import { useEffect } from "react";
const Login = () => {
   
//200 successful
    const [password,setPassword] = useState('');
    const [email,setEmail] = useState('');

    const [registorPassword,setRegistorPassword] = useState("");
    const [RegistorEamil, setRegistorEmail] = useState('');
    const [phone, setPhone] = useState('');
    const [name, setName] = useState('');
    
    const [redirection, setRedirection] = useState(false);
    
    const domain_name = "https://madneighborhood.tech:443";

    const RegistorSubmit = (e) => {
        const form = document.getElementById("regisform");
        form.addEventListener("submit", (e) => {
            e.preventDefault();
            const formData = new FormData(form);
            const data = Object.fromEntries(formData.entries());
            fetch(`https://madneighborhood.tech:443/register?email=${data.email}&name=${data.name}&phone=${data.phone}&password=${data.password}`, {
                method: "POST",
            }).then(res => {
                if(res.status === 200) {
                return res.json;}
                else {
                    alert("Sorry, your registion Fails");
                }
            }).then( data => {
                alert("Welcome! Please Login");
            }).catch(e => {
                alert("Sorry, your registion Fails");
            })
        })
    }
    

    const onLoad = () => {
        const form = document.getElementById("loginform")
        form.addEventListener("submit", (e) => {
            e.preventDefault();
            console.log("3");
            const formData = new FormData(form);
            const data = Object.fromEntries(formData.entries());
            console.log("1");
            // madneighborhood.tech
            // perform a fetch request as if it was a form submission
            
            fetch(`http://madneighborhood.tech:443/login?email=${data.email}&password=${data.password}`, {
                method: "POST",
            }).then( (res) => {
                if(res.ok) {
                    window.location.href = '/home';
                    console.log("set");
                    return res.json();
                } else {
                    alert("Login Fails, status:" + res.status);
                    console.log("set2");
                }
            }).then( data => {
                console.log("Data Set")
            }).catch (e => {
                alert("Login Fails:"+e.message);
                console.log("Data set2");
            })})
            
    }
    
/*
    useEffect ( () => {
        if(redirection){
            console.log("Redirecting...");
            setRedirection(false);
            window.location.href = '/home';
        }
    },[redirection])
*/

    return (
    
        <div className="Login">
            <div className="back">
                <h2>Connect with Neighborhood</h2>
                <form id="loginform" action={`http://madneighborhood.tech:443/login`} method="POST"  onSubmit={onLoad} >
                     <h1>Sign-in</h1>
                        <label>
                            <input
                            id="email-input"
                            type="email"
                            name="email" 
                            placeholder="Please Enter Your Email"
                            spellCheck="false"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required />
                        </label>
                        <label>
                            <input
                            id="pass-input" 
                            type="password" 
                            name="password" 
                            placeholder="Password"
                            spellCheck="false"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required />
                        </label>
                        <button type="submit">Continue</button>
                </form>
            </div>

            <div className="back">
                <h2>Don't Have An Account?</h2>
                <form id="regisform" onSubmit={RegistorSubmit}>
                    <h1>Register</h1>
                    <label>
                        <input 
                        id="user-input" 
                        type="text" 
                        name="name" 
                        placeholder="Name" 
                        spellCheck="false" 
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required />
                    </label>
                    <label>
                        <input 
                        id="email-input" 
                        type="email" 
                        name="email" 
                        placeholder="Email" 
                        spellCheck="false" 
                        value={RegistorEamil}
                        onChange={(e) => setRegistorEmail (e.target.value)}
                        required />
                    </label>
                    <label>
                        <input 
                        id="phone-input" 
                        type="tel" 
                        name="phone" 
                        placeholder="Phone Number" 
                        spellCheck="false" 
                        value={phone}
                        onChange={(e) => setPhone (e.target.value)}
                        required />
                    </label>
                    <label>
                            <input
                            id="pass-input" 
                            type="password" 
                            name="password" 
                            placeholder="Password"
                            spellCheck="false"
                            value={registorPassword}
                            onChange={(e) => setRegistorPassword(e.target.value)}
                            required />
                        </label>
                    <button type="submit">Submit</button>
                </form>

            </div>
        </div>
      );
}
 
export default Login;