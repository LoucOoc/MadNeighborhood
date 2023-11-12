
import './App.css';
// import itemImg from 'madneighborhood.tech/item?id='
// import itemDesc from 'madneighborhood.tech/item?id='
// import BorrowScreen from "./borrow.js";
import { AiOutlineClockCircle } from 'react-icons/ai';
import { AiOutlineSend } from 'react-icons/ai';
import React, { useState } from "react";
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import {LiaCommentsSolid} from "react-icons/lia";
// import sellerInfo from 'madneighborhood.tech/seller?id='
function ItemPage() {
  const userId = "123";
  const sellerId = "23";
  const itemId = "3";
  const itemName = "lawnmower";
  const sellerInfo = "john smith";
  const itemDesc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
  const logo = "";
  const sellerImg = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fi.pinimg.com%2Foriginals%2F07%2F33%2Fba%2F0733ba760b29378474dea0fdbcb97107.png&f=1&nofb=1&ipt=ec502a3eaa28d90bab7a6bfbb92e1cbc080e78199a2a9c2dafe85e91706a7951&ipo=images";
  const itemImg = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fcolbertondemand.com%2Fwp-content%2Fuploads%2F2021%2F05%2Flawn-mower-gardening-m...ut-grass-1593898-1.jpg&f=1&nofb=1&ipt=43181f7bc3ce6cd1884165ca65dd41ee49aeffb80bb75a1fbe1df5c417f3d3c0&ipo=images"; 
  const [vis, setVis] = useState(["hidden", "translate(-50%, -50%) scale(0.1)"]);
  const TopBar = (<nav>
    <div class="pages">
      <img src={logo} alt="logo" class="logo"></img>
      <div class="home">Hub</div>
      <div class="chat">Chat</div>
      <div class="inventory">Your Items</div>
    </div>
    <button class="sign-up">Sign Up</button>
</nav>);

  // const Comment = ({comments}) => {
  //   return (
  //     <>
  //     {comments.map((comment) => (
  //       <div className="Comment">
  //     <div className="user">
  //       <img className="user-img" src="https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.pngfind.com%2Fpngs%2Fm%2F676-6764065_default-profile-picture-transparent-hd-png-download.png&f=1&nofb=1&ipt=c10f8e3d8d0a8ec3c044b7f96714d137e4e9fff5924d8b03eb242da2e8947391&ipo=images"></img>
  //       <p className="user-desc">comments["userName"]</p>
  //     </div>
  //     <p>comments["content"]</p>
  //   </div>  
  //   ))}
  //   </>
  //   );
  // };

  // const comments = json.parse(fetch(`http://madneighborhood.tech:443/get_comments?user_id=${userId}&post_id=${itemId}`, {
  //   method: "GET"
  // }));
const visibility = {visibility : vis[0], transform: vis[1], transition: "all 0.4s ease-in-out"};
const onChange = dates => {
    console.log(dates);
    // fetch("domain_name/update_item_usage?community={name}&item={itemId}&start={}&end={}", )
};
const BorrowScreen =     
(
    <div class="purchase-screen" style={visibility}>
        <h1>Almost there!</h1>
        <p>Review the information and click confirm when ready.</p>
        <Calendar selectRange={true} onChange={onChange}></Calendar>
        <button class="confirm" onClick={() => {
            // fetch("madneighborhood.tech/", {
            //     method: "POST",  
            //     body: {
            //         buyer: userId,
            //         seller: sellerId,
            //         item: itemId
            //     }
            // });
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

                  // fetch("madneighborhood.tech/", {
                  //   method: "POST",
                  //   body: {
                  //     buyer: buyerID,
                  //     seller: sellerId,
                  //     item: itemId
                  //   }
                  // });
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
                  {/* {Comments} */}
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

export default ItemPage;
