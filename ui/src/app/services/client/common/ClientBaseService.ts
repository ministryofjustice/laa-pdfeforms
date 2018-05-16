import { environment } from '../../../../environments/environment';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';

export class ClientBaseService {
    API_URL = environment.API_URL;
    protected http: HttpClient;
}