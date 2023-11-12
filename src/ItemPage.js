import TopBar from './TopBar.js';
import './App.css';
import { Routes, Route, useParams } from 'react-router-dom';
import { AiOutlineClockCircle } from 'react-icons/ai';
import { AiOutlineSend } from 'react-icons/ai';
import React, { useState } from "react";
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import {LiaCommentsSolid} from "react-icons/lia";
// function ItemPage({userId, token, item, postId, sellerId, startDate, endDate}) {
function ItemPage({userId, token}) {
  const post_id = useParams()["id"];
  const [commentList, setComments] = useState([]);
  startDate = new Date(startDate.split("/")[0], parseInt(startDate.split("/")[1]) - 1, startDate.split("/")[2]);
  endDate = new Date(endDate.split("/")[0], parseInt(endDate.split("/")[1]) - 1, endDate.split("/")[2]);
  const itemName = "lawnmower";
  const sellerInfo = "john smith";
  const itemDesc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
  const logo = "";
  const sellerImg = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fi.pinimg.com%2Foriginals%2F07%2F33%2Fba%2F0733ba760b29378474dea0fdbcb97107.png&f=1&nofb=1&ipt=ec502a3eaa28d90bab7a6bfbb92e1cbc080e78199a2a9c2dafe85e91706a7951&ipo=images";
  const itemImg = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fcolbertondemand.com%2Fwp-content%2Fuploads%2F2021%2F05%2Flawn-mower-gardening-m...ut-grass-1593898-1.jpg&f=1&nofb=1&ipt=43181f7bc3ce6cd1884165ca65dd41ee49aeffb80bb75a1fbe1df5c417f3d3c0&ipo=images"; 
  const [vis, setVis] = useState(["hidden", "translate(-50%, -50%) scale(0.1)"]);
  const posts = fetch(`http://madneighborhood.tech:443/posts?page=${0}`, {
    method: "GET",
  }).then((res) => {
      if (res.status === 200)
        return res.json();
  }).then((data) => {
      console.log(data);
      // page_data = data;
  }).catch((e) => {
    alert("getpost failed");
  });
  let currPost;
  for (let post of posts) {
    if (post.id === post_id)
      currPost = post;
  }
  //a@gmail.com
//302 303
//   let page_data = undefined;
const comments = fetch(`https://madneighborhood.tech/get_comments?personal_token=${token}&post_id=${postId}
`, {
  method: "GET"
  // headers: {"Content-Type" : "application/json"},
  // body: JSON.stringify({
  //   token: token,
  //   postId: postId,
  // } )
}).then((res) => {
  if (res.ok) {
    console.log(res);
  } else {
    alert("get comments failed non 200");
  }
}).then(data => {
  // console.log(data);
    setComments(data);
}).catch(e => {
  alert("get comments failed");
});
  const Comments = ({commentList}) => (
      <>
      {commentList.map((comment) => (
        <div className="Comment">
      <div className="user">
        <img className="user-img" src="https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.pngfind.com%2Fpngs%2Fm%2F676-6764065_default-profile-picture-transparent-hd-png-download.png&f=1&nofb=1&ipt=c10f8e3d8d0a8ec3c044b7f96714d137e4e9fff5924d8b03eb242da2e8947391&ipo=images"></img>
        <p className="user-desc">{comment["userName"]}</p>
      </div>
      <p>{comment["content"]}</p>
    </div>  
    ))}
    </>
    );

const visibility = {visibility : vis[0], transform: vis[1], transition: "all 0.4s ease-in-out"};
const currDate = new Date();
let end = [];
const onChange = dates => {
    end = dates; 
};

const BorrowScreen =     
(
    <div class="purchase-screen" style={visibility}>
        <h1>Almost there!</h1>
        <p>Review the information and click confirm when ready.</p>
        <Calendar selectRange={true} onChange={onChange} defaultValue={startDate} minDate={startDate} maxDate={endDate}></Calendar>
        <button class="action thin" onClick={() => {
          fetch(`madneighborhood/create_checkout?personal_token=${token}&post_id=${postId}&end=${end[0]}
          `, {
            method: "POST",
            headers: {"Content-Type" : "application/json"},
            body: {
              token: token,
              postId: postId,
              end: {year: end[1].getYear(), month: end[1].getMonth(), day: end[1].getDay()}
            } 
          }).then((res) => {
            if (res.status === 200) {
              return;
            } else {
              alert("Checkout failed");
            }
          }).then(data => {

          }).catch(e => {
            alert("Checkout");
          });
            setVis((vis[0] === 'hidden') ? ["visible", "translate(-50%, -50%) scale(1)"] : ["hidden", "translate(-50%, -50%) scale(0.1)"]);
        }}>Confirm</button>
        </div>
);
  const item = {
    itemImg: itemImg,
    itemDesc: itemDesc,
    itemName: itemName
  };
  return (
    <div class="ItemPage">
          <div class="top">
          {TopBar}
          </div>
          {BorrowScreen}
      <div class="content">

          <div class="item-info">
            <p class="item-name">{itemName}</p>
            <img src={itemImg} alt="" class="item-img"></img>
          </div>
          <div class="listing-container">
            
                <div class="listing-info">
                  <p class="l-info-text"><AiOutlineClockCircle />Time left: </p>
                  <p class="l-info-text"><LiaCommentsSolid/>Comments: </p>
                <div class="user">
                    <img src={sellerImg} class="user-img"></img>
                    <p class="user-desc">{sellerInfo}</p>
                </div>
            </div>  
                <button class="action" onClick={() => {
                  //get popup
                  // console.log(BorrowScreen.style.display);
                  setVis((vis[0] === 'hidden') ? ["visible", "translate(-50%, -50%) scale(1)"] : ["hidden", "translate(-50%, -50%) scale(0.1)"]);
                }}>Borrow Item</button> 
            
          </div>
              <div class="item-description">
                  <h2 class="description-header">Item Description: </h2>
                  <p class="description-text">{itemDesc}</p>
              </div>
              <div class="comments">
                <h2>Comments:</h2>
                <textarea class="comment-box"></textarea>
                <button class="circular action inner-icon"><AiOutlineSend/></button>
                <div class="comment-section">
                  {Comments}
                  <div class="comment">
                   <div class="user">
                     <img class="user-img" src="https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.pngfind.com%2Fpngs%2Fm%2F676-6764065_default-profile-picture-transparent-hd-png-download.png&f=1&nofb=1&ipt=c10f8e3d8d0a8ec3c044b7f96714d137e4e9fff5924d8b03eb242da2e8947391&ipo=images"></img>
                     <p class="user-desc"></p>
                   </div>

                  </div>
                    <div class="comment">
                    <div class="user">
                      <img class="user-img" src="https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.pngfind.com%2Fpngs%2Fm%2F676-6764065_default-profile-picture-transparent-hd-png-download.png&f=1&nofb=1&ipt=c10f8e3d8d0a8ec3c044b7f96714d137e4e9fff5924d8b03eb242da2e8947391&ipo=images"></img>
                      <p class="user-desc">lorem ipsum</p>
                    </div>
                    <p class="description-text left-margin">
                    "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?"
                    </p>
                    </div>
                </div>
              </div>
      </div>
    </div>
  );
}
//TODO set max/min dates for calendar, style popup
export default ItemPage;
