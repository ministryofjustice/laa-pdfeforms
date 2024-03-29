import { Address } from './Address';
import { Disabilities } from './Disabilities';

export class Client {
    id : number;
    ufn : string;
    title : string;
    forename : string;
    surname : string;
    residenceAddress = new Address();
    dateOfBirth : Date;
    age : number;
    niNumber : string;
    employmentStatus : string;
    relationshipStatus : string;
    telephoneNumber : string;
    sameAsResidenceAddress : string;
    correspondenceAddress = new Address() ;
    ethnicity : string ;
    sourceOfBusiness : string;
    existingClient : string;
    requestSpecificSolicitor : string;
    requestSpecificSolicitorText : string;
    policeStation1 : string;
    magistrateCourt : string;
    crownCourt : string;
    otherCourt : string;
    nationality : string;
    venue : string;
    venueOther : string;
    allegation : string;
    previousConviction : string;
    fundingDate : Date;
    adviceAndAssistance : string;
    proofOfBenefitsRequested : string;
    policeStation2 : string;
    preOrderWork : string;
    repOrderAppliedFor : string;
    repOrderGranted : string;
    crm3Advocate : string;
    conflictCheck : string;
    conflictCheckName : string;
    conflictCheckDate : Date;
    riskAssessmentDone : string;
    riskAssessmentType : string;
    coAccused : string;
    disabilities = new Array<Disabilities>();
    constructor(){}

}
