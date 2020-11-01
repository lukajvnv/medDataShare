import React from 'react';
import logo from '../assets/medDataShare_logo.jpg'

const Loader = () => (
    <div id='loader' style={{
        width: '50vw',
        height: '50vh',
        // position: 'relative',
        zIndex: 90000,
        background: 'white',
        // top: 0,
        // left: 0
    }}>
        <div style={{
            position: 'absolute',
            left: '0px',
            top: '0px',
            width: '100%',
            height: '100%',
            backgroundImage: `url(${logo})`,
            backgroundPosition: 'center',
            backgroundRepeat: 'no-repeat',
            backgroundSize: 'cover'
        }}>
        </div>
    </div>
);

export default Loader;