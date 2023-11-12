import useFetch from "./useFetch";
import PostList from "./PostList";
import Alert from "./Alert";
import { Link } from 'react-router-dom';
import Navbar from './Navbar';
import { useEffect, useState } from "react";

const Home = (props) => {

   
    const [number, setNumber] = useState(1);;// 1 by default, each time touch down screen, ++
    const key = "user_id";
    const domain_name = "https://madneighborhood.tech:443";
    const [userId, setUserId] = useState("");

    const getCookieValue = (key) => {
        const cookies = document.cookie.split('; ');
        for (const cookie of cookies) {
          const [cookieName, cookieValue] = cookie.split('=');
          console.log(cookies);
          if (cookieName === key) {
            return cookieValue;
          }
        }
        return null; 
      };
      
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

    const {data : posts, error: postError} = useFetch(`${domain_name}/posts?&page=${number}`);
    const {data : thisUser, error : lendingError} = useFetch(`${domain_name}/get_user?id=${userId}`);

    const scrollEvent = () => {
        setNumber(number + 1);
    };

    return ( 
 
       
        <div onScroll={scrollEvent}>
             <Navbar />
            {posts&&<PostList posts={posts} ></PostList>}
            {thisUser&&<Alert lendings={thisUser.Item_checked_out}/>}
           <button className="new">
           <Link to="/new">Post</Link>
           </button>
        </div>
     );
}
 
export default Home;