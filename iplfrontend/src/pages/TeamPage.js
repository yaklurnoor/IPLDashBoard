import React from 'react';
import { MatchDetailCard } from './MatchDetailCard';
import { MatchSmallCard } from './MatchSmallCard';

export const  TeamPage = () => {
    return (
        <div className="TeamPage">
        <h1>Team Name </h1>
       <MatchDetailCard/>
       <MatchSmallCard/>
       <MatchSmallCard/>
       <MatchSmallCard/>       
        </div>
    );
}

