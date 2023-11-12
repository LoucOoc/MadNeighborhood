import { BrowserRouter, Routes, Route } from "react-router-dom";
import './App.css';
import Login from './Login';
import Home from './Home';
import PostDetail from './PostDetail';
import NewPost from './NewPost';

function App() {
  
  return (
    
    <BrowserRouter>
      <div className="App">
          <div className="content">
          
            <Routes>
              <Route exact path="/" element = {<Login />} />
              <Route path='/home' element={<Home />}/>
              <Route path="/posts/:id" element={<PostDetail />}/>
              <Route path="/new" element={<NewPost/>} />
            </Routes>
          </div>
      </div>
    </BrowserRouter>
    
  );
}

export default App;
