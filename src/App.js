import { BrowserRouter, Routes, Route, useParams } from "react-router-dom";
import './App.css';
import Login from './Login';
import Home from './Home';
import PostDetail from './PostDetail';
import NewPost from './NewPost';
import { useEffect, useState } from "react";
import ItemPage from "./ItemPage";
function App() {
  let key = "user_id";
  const [userId, setUserId] = useState("");
  const [pid, setPID] = useState("");
  let param = useParams();
  const getCookieValue = ((key) => {
    const cookies = document.cookie.split('; ');
    for (const cookie of cookies) {
      const [cookieName, cookieValue] = cookie.split('=');
      console.log(cookies);
      if (cookieName === key) {
        return cookieValue;
      }
    }
    return null; 
  });
  
useEffect(() => {
    const temp = getCookieValue(key);
    setTimeout(() => {

    },1000);
    if(temp==null){
        console.log("Please Login");
       // window.location.href = '/';
    } else
    setUserId(temp);
  }, []);
  key = "personal_id";
  const getPidValue = ((key) => {
    const cookies = document.cookie.split('; ');
    for (const cookie of cookies) {
      const [cookieName, cookieValue] = cookie.split('=');
      console.log(cookies);
      if (cookieName === key) {
        return cookieValue;
      }
    }
    return null; 
  });
  
useEffect(() => {
    const temp = getCookieValue(key);
    setTimeout(() => {

    },1000);
    if(temp==null){
        console.log("Please Login");
       // window.location.href = '/';
    } else
    setPID(temp);
  }, []);
  return (
    
    <BrowserRouter>
      <div className="App">
          <div className="content">
          
            <Routes>
              <Route exact path="/" element = {<Login />} />
              <Route path='/home' element={<Home />}/>
              <Route path="/posts/:id" element={<ItemPage  userId={userId} token={pid}/>}/>
              <Route path="/new" element={<NewPost/>} />
            </Routes>
          </div>
      </div>
    </BrowserRouter>
    
  );
}

export default App;
