import './App.css';
import Navbar from './Navbar.js';
import './TopBar.js';
function InventoryPage({userId}) {
    const data = fetch(`/get_user?id=${userId}`, {
        method: "GET",
        body: JSON.stringify({user_id: userId})
    }).then((res) => {
        if (res.status == 200)
            return res.json();
        else
            alert("User lookup failed");
    }).then((data) => {

    }).catch((e) => {
        alert("User lookup failed");
    });
    const others = data["items_others_checked"].map((post_id) => {
        fetch(`domain/get_post?id=${post_id}`, {
            method: "GET",
            body: JSON.stringify({post_id : post_id})
        }).then((res) => {
            if (res.status === 200)
                return res.json();

        }).then((data) => {

        }).catch((e) => {
        });
    });
    const checkedOut = data["items_checked_out"].map((post_id) => {
        fetch(`domain/get_post?id=${post_id}`, {
            method: "GET",
            body: JSON.stringify({post_id : post_id})
        }).then((res) => {
            if (res.status === 200)
                return res.json();

        }).then((data) => {

        }).catch((e) => {
        });
    });
    const others_checked_out = ({others}) => {
        others.map((post) => {
            return (<div class="item">
                <p>{post["title"]}</p>
                <p>{post["content"]}</p>
                <p>{post["end_date"]}</p>
            </div>);
        });
    };
    const user_checked = ({checkedOut}) => { 
        others.map((post) => {
            return (<div class="item">
                <p>{post["title"]}</p>
                <p>{post["content"]}</p>
                <p>{post["end_date"]}</p>
            </div>);
        });
    }
    return (
        <div className='inventoryPage'>
            <div class="top">
                <Navbar />
            </div>
            <div className='content'>
                <h1>What other users have checked out from you:</h1>
                {others_checked_out}

            </div>
        </div>
    )
    
}