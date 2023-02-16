import { AdvancedSearchComponent } from './components/advanced-search/advanced-search.component';
import { GeoSearchComponent } from './components/geo-search/geo-search.component';
import { SearchComponent } from './components/search/search.component';
import { RegisterComponent } from './components/register/register.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [

  {
    path: 'register',
    component: RegisterComponent
  },

  {
    path: '',
    component: SearchComponent,
    pathMatch: 'full', 
  },

  {
    path: 'geoSearch',
    component: GeoSearchComponent,
  },

  {
    path: 'advancedSearch',
    component: AdvancedSearchComponent,
  },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
